package com.asdec.hauling.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hauling_logs")
data class HaulingLog(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val driverName: String,
    val loadWeight: Double,
    val timestamp: Long = System.currentTimeMillis(),

    // THE SYNC FLAG:
    // This is the key to your server-side logic.
    // Default to 'false' so new entries are marked for upload.
    val isSynced: Boolean = false
)