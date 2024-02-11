package com.example.parkshare_new.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Parking (
    @PrimaryKey val address: String,
    val avatar: String,
    val city: String,
    var isChecked: Boolean)
