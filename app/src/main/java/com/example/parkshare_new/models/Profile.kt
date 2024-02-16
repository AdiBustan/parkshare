package com.example.parkshare_new.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Profile (
    @PrimaryKey val userName: String,
    val numberOfHelps: Int,
    val numberOfUsed: Int,
    val posts: List<Parking>
)
