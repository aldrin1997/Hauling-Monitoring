package com.asdec.hauling.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trucks")
data class Truck(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,      // Changed to Long with auto-generation
    val batchId: String,   // This links multiple trucks to one batch
    val haulerName: String,
    val plateNo: String,
    val length: Double,
    val width: Double,
    val height: Double,
    val cum: Double,
    val timeIn: String,
    val timeOut: String,
    val status: String = "IN_PROGRESS",
    val isSynced: Boolean = false
)