package com.example.parkshare_new.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Parking (
    @PrimaryKey val address: String,
    val avatar: String,
    val city: String,
    var isChecked: Boolean) {

    companion object {
        const val ADDRESS_KEY = "address"
        const val AVATAR_KEY = "avatar"
        const val CITY_KEY = "city"
        const val IS_CHECKED_KEY = "isChecked"

        fun fromJSON(json : Map<String, Any>): Parking {
            val address = json[ADDRESS_KEY] as? String ?: ""
            val avatar = json[AVATAR_KEY] as? String?: ""
            val city = json[CITY_KEY] as? String ?: ""
            val isChecked = json[IS_CHECKED_KEY] as? Boolean ?: false

            return Parking(address, avatar, city, isChecked)

        }
    }

    val json: Map<String, Any> get() {
        return hashMapOf(
            "address" to this.address,
            "avatar" to this.avatar,
            "city" to this.city,
            "isChecked" to this.isChecked,
        )
    }
}
