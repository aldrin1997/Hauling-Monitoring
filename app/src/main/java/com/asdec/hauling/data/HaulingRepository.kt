package com.asdec.hauling.data

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import java.io.File
import java.util.UUID

class HaulingRepository(
    private val appDao: AppDao,
    private val userDao: UserDao
) {
    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance().reference

    val allBatches: Flow<List<Batch>> = appDao.getAllBatches()

    // --- LOGIN & AUTH ---

    suspend fun initializeDefaultAdmin() {
        try {
            val adminExists = userDao.getUserByUsername("admin")
            if (adminExists == null) {
                val defaultAdmin = User(
                    id = UUID.randomUUID().toString(),
                    username = "admin",
                    password = "password123",
                    role = "ADMIN"
                )
                userDao.createUser(defaultAdmin)
            }
        } catch (e: Exception) {
            Log.e("HAULING_DEBUG", "Failed to seed admin", e)
        }
    }

    suspend fun getUserByUsername(username: String): User? = userDao.getUserByUsername(username)

    suspend fun createUser(user: User) {
        try {
            userDao.createUser(user)
        } catch (e: Exception) {
            Log.e("HAULING_DEBUG", "Error creating user", e)
            throw e
        }
    }

    // --- CLOUD STORAGE LOGIC ---

    suspend fun uploadPhotoAndGetUrl(batchId: String, truckId: String, type: String, localPath: String): String? {
        val file = File(localPath)
        if (!file.exists()) return null

        val photoRef = FirebaseStorage.getInstance().reference
            .child("hauling_photos")
            .child(batchId)
            .child("${truckId}_${type}.jpg")

        return try {
            photoRef.putFile(Uri.fromFile(file)).await()
            val downloadUrl = photoRef.downloadUrl.await()
            downloadUrl.toString()
        } catch (e: Exception) {
            Log.e("HAULING_DEBUG", "Storage Upload Failed", e)
            null
        }
    }

    // --- NEW SIMULTANEOUS TRUCK LOGIC ---

    /**
     * INITIALIZE TRUCK (Entrance)
     * Call this when a truck arrives. It sets status to IN_PROGRESS.
     * This allows the user to open multiple truck entries at once.
     */
    suspend fun startTruckEntry(
        batchId: String,
        hauler: String,
        plate: String,
        timeIn: String
    ): Long {
        val truck = Truck(
            batchId = batchId,
            haulerName = hauler,
            plateNo = plate,
            length = 0.0,
            width = 0.0,
            height = 0.0,
            cum = 0.0,
            timeIn = timeIn,
            timeOut = "",
            status = "IN_PROGRESS",
            isSynced = false
        )
        val newTruckId = appDao.insertTruck(truck)
        stageBatch(batchId)
        return newTruckId
    }

    /**
     * COMPLETE TRUCK (Exit)
     * Call this when the truck is leaving. Updates dimensions and completes the record.
     */
    suspend fun completeTruckEntry(
        truckId: Long,
        l: Double,
        w: Double,
        h: Double,
        timeOut: String
    ) {
        try {
            val truck = appDao.getTruckById(truckId)
            if (truck != null) {
                val updatedTruck = truck.copy(
                    length = l,
                    width = w,
                    height = h,
                    cum = l * w * h,
                    timeOut = timeOut,
                    status = "COMPLETED",
                    isSynced = false
                )
                appDao.updateTruck(updatedTruck)
                Log.d("HAULING_DEBUG", "Truck $truckId moved to COMPLETED status")
            }
        } catch (e: Exception) {
            Log.e("HAULING_DEBUG", "Error completing truck entry", e)
        }
    }

    /**
     * Fetches current trucks in the batch that haven't exited yet.
     */
    suspend fun getInProgressTrucks(batchId: String): List<Truck> {
        return appDao.getTrucksForBatch(batchId).first().filter { it.status == "IN_PROGRESS" }
    }

    // --- SYNC & RESCAN LOGIC ---

    suspend fun syncAllData() {
        try {
            Log.d("HAULING_SYNC", "Starting Full Rescan & Sync...")

            // 1. Sync Batches
            appDao.getUnsyncedBatches().forEach { batch ->
                val batchMap = hashMapOf(
                    "id" to batch.id,
                    "companyId" to batch.companyId,
                    "projectId" to batch.projectId,
                    "date" to batch.date,
                    "status" to batch.status,
                    "createdBy" to batch.createdBy,
                    "synced" to true
                )
                firestore.collection("batches").document(batch.id).set(batchMap).await()
                appDao.updateBatchSyncStatus(batch.id, true)
            }

            // 2. Sync Completed Trucks
            // We only sync COMPLETED trucks to avoid showing partial data on the Admin Panel
            appDao.getUnsyncedTrucks().forEach { truck ->
                if (truck.status == "COMPLETED") {
                    val firestoreId = "truck_${truck.batchId}_${truck.id}"
                    val truckMap = hashMapOf(
                        "id" to truck.id,
                        "batchid" to truck.batchId,
                        "haulerName" to truck.haulerName,
                        "plateNo" to truck.plateNo,
                        "length" to truck.length,
                        "width" to truck.width,
                        "height" to truck.height,
                        "cum" to truck.cum,
                        "timeIn" to truck.timeIn,
                        "timeOut" to truck.timeOut,
                        "status" to truck.status,
                        "synced" to true
                    )
                    firestore.collection("trucks").document(firestoreId).set(truckMap).await()
                    appDao.updateTruckSyncStatus(truck.id, true)
                }
            }

            // 3. Rescan & Repair Photos
            val allPhotos = appDao.getAllPhotosSync()
            allPhotos.forEach { photo ->
                try {
                    if (photo.photoPath.startsWith("/storage")) {
                        val cloudUrl = uploadPhotoAndGetUrl(photo.batchId, photo.truckId, photo.photoType, photo.photoPath)
                        if (cloudUrl != null) {
                            val photoMap = hashMapOf(
                                "id" to photo.id,
                                "batchid" to photo.batchId,
                                "truckId" to photo.truckId,
                                "photoPath" to cloudUrl,
                                "photoType" to photo.photoType,
                                "timestamp" to photo.timestamp,
                                "synced" to true
                            )
                            firestore.collection("photos").document(photo.id).set(photoMap).await()
                            appDao.updatePhotoPath(photo.id, cloudUrl)
                            appDao.updatePhotoSyncStatus(photo.id, true)
                        }
                    } else if (!photo.isSynced) {
                        val photoMap = hashMapOf(
                            "id" to photo.id,
                            "batchid" to photo.batchId,
                            "truckId" to photo.truckId,
                            "photoPath" to photo.photoPath,
                            "photoType" to photo.photoType,
                            "timestamp" to photo.timestamp,
                            "synced" to true
                        )
                        firestore.collection("photos").document(photo.id).set(photoMap).await()
                        appDao.updatePhotoSyncStatus(photo.id, true)
                    }
                } catch (pe: Exception) {
                    Log.e("HAULING_SYNC", "Photo sync error", pe)
                }
            }

            Log.d("HAULING_SYNC", "Full Sync Completed")
        } catch (e: Exception) {
            Log.e("HAULING_SYNC", "Global Sync Failure", e)
            throw e
        }
    }

    // --- BATCH & PHOTO MANAGEMENT ---

    suspend fun createNewBatch(userId: String, companyId: String, projectId: String, forcedId: String? = null): String {
        val existingId = forcedId ?: UUID.randomUUID().toString()
        val existingBatch = appDao.getBatchById(existingId)
        if (existingBatch == null) {
            val newBatch = Batch(id = existingId, companyId = companyId, projectId = projectId, date = System.currentTimeMillis(), status = "DRAFT", createdBy = userId, isSynced = false)
            appDao.insertBatch(newBatch)
        }
        return existingId
    }

    suspend fun getTrucksByBatchId(batchId: String): List<Truck> {
        return try { appDao.getTrucksForBatch(batchId).first() } catch (e: Exception) { emptyList() }
    }

    private suspend fun stageBatch(batchId: String) {
        val batch = appDao.getBatchById(batchId)
        if (batch != null) {
            appDao.updateBatch(batch.copy(status = "STAGED", isSynced = false))
        }
    }

    suspend fun savePhoto(batchId: String, type: String, path: String, truckId: String) {
        if (path.isBlank()) return
        val photo = Photo(
            id = UUID.randomUUID().toString(),
            batchId = batchId,
            truckId = truckId,
            photoPath = path,
            photoType = type,
            timestamp = System.currentTimeMillis(),
            isSynced = false
        )
        appDao.insertPhoto(photo)
    }

    suspend fun deleteTruck(truckId: Long) {
        appDao.deleteTruckById(truckId)
        appDao.deletePhotosByTruckId(truckId.toString())
    }

    suspend fun deleteBatch(batchId: String) {
        appDao.deleteBatchById(batchId)
        appDao.deleteTrucksByBatchId(batchId)
        appDao.deletePhotosByBatchId(batchId)
    }
}