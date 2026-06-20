package com.asdec.hauling.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "batches")
data class Batch(
    @PrimaryKey
    val id: String,
    val companyId: String,
    val projectId: String,
    val date: Long,
    val status: String,
    val createdBy: String,
    val isSynced: Boolean = false
)