package com.asdec.hauling.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: String, // Hidden unique ID
    val username: String,       // Login name
    val password: String,
    val fullName: String = "",
    val role: String = "USER"
)