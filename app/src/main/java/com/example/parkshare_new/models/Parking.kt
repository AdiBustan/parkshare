package com.example.parkshare_new.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity 
data class Parking (
    @PrimaryKey val timestamp: Long,
    val address: String,
    val avatar: String,
    val city: String,
    val owner: String,
    var isChecked: Boolean,
    var isUnavailable: Boolean
     ) {

    companion object {
        const val TIMESTAMP = "timestamp"
        const val ADDRESS_KEY = "address"
        const val AVATAR_KEY = "avatar"
        const val CITY_KEY = "city"
        const val OWNER_KEY = "owner"
        const val IS_CHECKED_KEY = "isChecked"
        const val IS_UNAVAILABLE = "isUnavailable"


        fun fromJSON(json : Map<String, Any>): Parking {
            val timestamp = json[TIMESTAMP] as? Long ?: 0
            val address = json[ADDRESS_KEY] as? String ?: ""
            val avatar = json[AVATAR_KEY] as? String ?: ""
            val city = json[CITY_KEY] as? String ?: ""
            val owner = json[OWNER_KEY] as? String ?: ""
            val isChecked = json[IS_CHECKED_KEY] as? Boolean ?: false
            val isUnavailable = json[IS_UNAVAILABLE] as? Boolean ?: false
            return Parking(timestamp, address, avatar, city, owner, isChecked, isUnavailable)
        }
    }

    val json: Map<String, Any> get() {
        return hashMapOf(
            "timestamp" to this.timestamp,
            "address" to this.address,
            "avatar" to this.avatar,
            "city" to this.city,
            "owner" to this.owner,
            "isChecked" to this.isChecked,
            "isUnavailable" to this.isUnavailable,
        )
    }
}
