package com.asdec.hauling.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    // --- Batches ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBatch(batch: Batch)

    @Query("SELECT * FROM batches ORDER BY date DESC")
    fun getAllBatches(): Flow<List<Batch>>

    @Query("SELECT * FROM batches WHERE id = :batchId LIMIT 1")
    suspend fun getBatchById(batchId: String): Batch?

    @Update
    suspend fun updateBatch(batch: Batch)

    // --- Trucks ---
    /**
     * Returns the Long ID of the inserted truck for photo linking.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTruck(truck: Truck): Long

    @Query("SELECT * FROM trucks WHERE batchId = :batchId")
    fun getTrucksForBatch(batchId: String): Flow<List<Truck>>

    @Query("SELECT * FROM trucks WHERE batchId = :batchId")
    suspend fun getTrucksForBatchSync(batchId: String): List<Truck>

    @Query("SELECT COUNT(*) FROM trucks WHERE batchId = :batchId")
    suspend fun getTruckCountForBatch(batchId: String): Int

    // --- Photos ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(photo: Photo)

    @Query("SELECT * FROM photos WHERE batchId = :batchId")
    suspend fun getPhotosForBatch(batchId: String): List<Photo>

    /**
     * CRITICAL FOR WEB SYNC:
     * Replaces the local file path with the Firebase Storage URL after upload.
     */
    @Query("UPDATE photos SET photoPath = :newPath WHERE id = :photoId")
    suspend fun updatePhotoPath(photoId: String, newPath: String)

    // --- Sync Queries for Firebase ---

    @Query("SELECT * FROM batches WHERE isSynced = 0")
    suspend fun getUnsyncedBatches(): List<Batch>

    @Query("SELECT * FROM trucks WHERE isSynced = 0")
    suspend fun getUnsyncedTrucks(): List<Truck>

    @Query("SELECT * FROM photos WHERE isSynced = 0")
    suspend fun getUnsyncedPhotos(): List<Photo>

    @Query("UPDATE batches SET isSynced = :status WHERE id = :id")
    suspend fun updateBatchSyncStatus(id: String, status: Boolean)

    @Query("UPDATE trucks SET isSynced = :status WHERE id = :id")
    suspend fun updateTruckSyncStatus(id: Long, status: Boolean)

    @Query("UPDATE photos SET isSynced = :status WHERE id = :id")
    suspend fun updatePhotoSyncStatus(id: String, status: Boolean)

    // --- Deletion Management (Cascading cleanup) ---

    @Query("DELETE FROM trucks WHERE id = :truckId")
    suspend fun deleteTruckById(truckId: Long)

    @Query("DELETE FROM photos WHERE truckId = :truckId")
    suspend fun deletePhotosByTruckId(truckId: String)

    @Query("DELETE FROM batches WHERE id = :batchId")
    suspend fun deleteBatchById(batchId: String)

    @Query("DELETE FROM trucks WHERE batchId = :batchId")
    suspend fun deleteTrucksByBatchId(batchId: String)

    @Query("DELETE FROM photos WHERE batchId = :batchId")
    suspend fun deletePhotosByBatchId(batchId: String)

    @Query("SELECT * FROM photos")
    suspend fun getAllPhotosSync(): List<Photo>

    @Query("SELECT * FROM trucks WHERE id = :truckId LIMIT 1")
    suspend fun getTruckById(truckId: Long): Truck?

    @Update
    suspend fun updateTruck(truck: Truck)
}