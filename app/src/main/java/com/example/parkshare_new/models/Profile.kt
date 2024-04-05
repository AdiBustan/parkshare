package com.example.parkshare_new.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Profile (
    @PrimaryKey val email: String,
    val userName: String,
    val faveCity: String,
    var avatar: String,
    var posts: List<Parking>)
{
    companion object {
        const val EMAIL_KEY = "email"
        const val USERNAME_KEY = "userName"
        const val AVATAR = "avatar"
        const val FAVE_CITY = "faveCity"
        const val POSTS = "posts"


        fun fromJSON(json : Map<String, Any>): Profile {
            val email = json[EMAIL_KEY] as? String ?: ""
            val username = json[USERNAME_KEY] as? String ?: ""
            val faveCity = json[FAVE_CITY] as? String ?: ""
            val avatar = json[AVATAR] as? String ?: ""
            val posts = json[POSTS] as? List<Parking> ?: listOf()
            return Profile(email, username, faveCity, avatar, posts)
        }
    }

    val json: Map<String, Any> get() {
        return hashMapOf(
            "email" to this.email,
            "userName" to this.userName,
            "faveCity" to this.faveCity,
            "avatar" to this.avatar,
            "posts" to this.posts
        )
    }
}
