package com.example.parkshare_new.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Profile (
    @PrimaryKey val email: String,
    val userName: String,
    var numberOfHelps: Int,
    var numberOfUsed: Int,
    var posts: List<Parking>)
{
    companion object {
        const val EMAIL_KEY = "email"
        const val USERNAME_KEY = "userName"
        const val NUM_OF_HELPS_KEY = "numberOfHelps"
        const val NUM_OF_USED_KEY = "numberOfUsed"
        const val POSTS = "posts"


        fun fromJSON(json : Map<String, Any>): Profile {
            val email = json[EMAIL_KEY] as? String ?: ""
            val username = json[USERNAME_KEY] as? String ?: ""
            val numberOfHelps = json[NUM_OF_HELPS_KEY] as? Int ?: 0
            val numberOfUsed = json[NUM_OF_USED_KEY] as? Int ?: 0
            val posts = json[POSTS] as? List<Parking> ?: listOf()
            return Profile(email, username, numberOfHelps, numberOfUsed, posts)
        }
    }

    val json: Map<String, Any> get() {
        return hashMapOf(
            "email" to this.email,
            "username" to this.userName,
            "numberOfHelps" to this.numberOfHelps,
            "numberOfUsed" to this.numberOfUsed,
            "posts" to this.posts
        )
    }
}
