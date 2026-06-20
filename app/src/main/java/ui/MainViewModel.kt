package com.asdec.hauling.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asdec.hauling.data.Batch
import com.asdec.hauling.data.HaulingRepository
import com.asdec.hauling.data.Truck
import com.asdec.hauling.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val repository: HaulingRepository) : ViewModel() {

    val batches: Flow<List<Batch>> = repository.allBatches

    // --- Auth State ---
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    // --- Form State ---
    var projectName by mutableStateOf("")
    var hauler by mutableStateOf("")
    var plate by mutableStateOf("")
    var L by mutableStateOf("")
    var W by mutableStateOf("")
    var H by mutableStateOf("")

    private val _photoPaths = MutableStateFlow<Map<String, String>>(emptyMap())
    val photoPaths = _photoPaths.asStateFlow()

    private val _selectedTrucks = MutableStateFlow<List<Truck>>(emptyList())
    val selectedTrucks = _selectedTrucks.asStateFlow()

    // --- New State for Simultaneous Handling ---
    private val _inProgressTrucks = MutableStateFlow<List<Truck>>(emptyList())
    val inProgressTrucks = _inProgressTrucks.asStateFlow()

    init {
        viewModelScope.launch {
            repository.initializeDefaultAdmin()
        }
    }

    /**
     * START TRUCK (Entrance Logic)
     * Allows adding multiple trucks in the "IN_PROGRESS" state.
     */
    fun startTruckEntry(batchId: String, timeIn: String, onComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                val creatorId = _currentUser.value?.username ?: "Unknown"
                val currentPhotos = _photoPaths.value

                withContext(Dispatchers.IO) {
                    // 1. Ensure Batch Exists
                    repository.createNewBatch(
                        userId = creatorId,
                        companyId = "ASDEC",
                        projectId = projectName,
                        forcedId = batchId
                    )

                    // 2. Insert Truck as IN_PROGRESS
                    val newTruckId = repository.startTruckEntry(
                        batchId = batchId,
                        hauler = hauler,
                        plate = plate,
                        timeIn = timeIn
                    )

                    // 3. Save photos associated with this specific truck
                    currentPhotos.forEach { (key, value) ->
                        // Expecting keys like "entrance", "side", etc.
                        val photoType = key.substringAfter("${batchId}_", key)
                        repository.savePhoto(
                            batchId = batchId,
                            type = photoType,
                            path = value,
                            truckId = newTruckId.toString()
                        )
                    }
                }

                withContext(Dispatchers.Main) {
                    resetForNextTruck()
                    loadInProgressTrucks(batchId)
                    onComplete()
                }
            } catch (e: Exception) {
                Log.e("HAULING_DEBUG", "Start Truck Error: ${e.message}", e)
            }
        }
    }

    /**
     * FINISH TRUCK (Exit Logic)
     * Updates an existing truck with dimensions and moves it to COMPLETED.
     */
    fun finishTruckEntry(truckId: Long, batchId: String, timeOut: String, onComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repository.completeTruckEntry(
                        truckId = truckId,
                        l = L.toDoubleOrNull() ?: 0.0,
                        w = W.toDoubleOrNull() ?: 0.0,
                        h = H.toDoubleOrNull() ?: 0.0,
                        timeOut = timeOut
                    )
                }
                withContext(Dispatchers.Main) {
                    resetForNextTruck()
                    loadTruckDetails(batchId) // Refresh full list
                    loadInProgressTrucks(batchId) // Refresh active list
                    onComplete()
                }
            } catch (e: Exception) {
                Log.e("HAULING_DEBUG", "Finish Truck Error: ${e.message}", e)
            }
        }
    }

    /**
     * Loads only trucks that are currently on-site (IN_PROGRESS)
     */
    fun loadInProgressTrucks(batchId: String) {
        viewModelScope.launch {
            try {
                val active = withContext(Dispatchers.IO) {
                    repository.getInProgressTrucks(batchId)
                }
                _inProgressTrucks.value = active
            } catch (e: Exception) {
                Log.e("HAULING_DEBUG", "Load In-Progress error", e)
            }
        }
    }

    /**
     * Delete an individual truck and refresh the UI list.
     */
    fun deleteTruck(truckId: Long, batchId: String) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repository.deleteTruck(truckId)
                }
                loadTruckDetails(batchId)
                loadInProgressTrucks(batchId)
                Log.d("HAULING_DEBUG", "Truck $truckId deleted successfully")
            } catch (e: Exception) {
                Log.e("HAULING_DEBUG", "Failed to delete truck", e)
            }
        }
    }

    /**
     * Delete an entire batch and all its associated trucks/photos.
     */
    fun deleteBatch(batchId: String) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repository.deleteBatch(batchId)
                }
                Log.d("HAULING_DEBUG", "Batch $batchId deleted successfully")
            } catch (e: Exception) {
                Log.e("HAULING_DEBUG", "Failed to delete batch", e)
            }
        }
    }

    suspend fun syncData() {
        withContext(Dispatchers.IO) {
            repository.syncAllData()
        }
    }

    fun login(username: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val user = repository.getUserByUsername(username)
                if (user != null && user.password == password) {
                    _currentUser.value = user
                    onResult(true)
                } else {
                    onResult(false)
                }
            } catch (e: Exception) {
                Log.e("HAULING_DEBUG", "Login error", e)
                onResult(false)
            }
        }
    }

    fun logout() {
        _currentUser.value = null
        clearForm()
    }

    // This is now legacy/unused but kept for structure compatibility
    fun saveFullEntry(
        batchId: String, projectName: String, hauler: String, plate: String,
        l: Double, w: Double, h: Double, timeIn: String, timeOut: String, onComplete: () -> Unit
    ) {
        // Redirect to start/finish logic or keep as a one-step save if needed
        viewModelScope.launch {
            startTruckEntry(batchId, timeIn) {
                // In a full entry, we immediately finish it
                viewModelScope.launch {
                    val active = repository.getInProgressTrucks(batchId)
                    val lastTruck = active.lastOrNull { it.plateNo == plate }
                    if (lastTruck != null) {
                        finishTruckEntry(lastTruck.id, batchId, timeOut, onComplete)
                    }
                }
            }
        }
    }

    fun loadTruckDetails(batchId: String) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val trucks = repository.getTrucksByBatchId(batchId)
                    // Note: Ensure repository has getPhotosForBatch(batchId)
                    // If you encounter an error here, check Repository function names.
                    val savedPhotos = repository.getTrucksByBatchId(batchId)

                    withContext(Dispatchers.Main) {
                        _selectedTrucks.value = trucks
                    }
                }
            } catch (e: Exception) {
                Log.e("HAULING_DEBUG", "Load error", e)
            }
        }
    }

    fun clearForm() {
        projectName = ""
        resetForNextTruck()
    }

    fun resetForNextTruck() {
        hauler = ""
        plate = ""
        L = ""
        W = ""
        H = ""
        _photoPaths.value = emptyMap()
    }

    fun updatePhotoPath(batchId: String, photoType: String, path: String) {
        val key = "${batchId}_$photoType"
        _photoPaths.update { it + (key to path) }
    }

    fun getPhotoPath(batchId: String, photoType: String): String? =
        _photoPaths.value["${batchId}_$photoType"]
}