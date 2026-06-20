package com.asdec.hauling.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class Photo(
    @PrimaryKey
    val id: String,
    val batchId: String,   // Added: To link photos to the batch for the Details screen
    val truckId: String,   // Maintained: Link to specific truck if needed
    val photoPath: String,
    val photoType: String, // e.g., "Front", "Back"
    val timestamp: Long,
    val isSynced: Boolean = false// Maintained: To track when the photo was taken
)