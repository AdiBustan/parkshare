package com.example.parkshare_new.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class LocalUser(
    @PrimaryKey val email: String,
    val username: String,
    val faveCity: String,
    val avatar: String,
    var timestamp: Long
)