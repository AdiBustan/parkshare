package com.example.parkshare_new.models

import android.location.Address
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class FirebaseModel {
    private val db = Firebase.firestore

    companion object {
        const val PARKING_SPOTS_COLLECTION_PATH = "parkingSpots"
        const val PROFILES_COLLECTION_PATH = "profiles"
    }

    init {
       val settings = firestoreSettings {
           setLocalCacheSettings(memoryCacheSettings {  })
       }
        db.firestoreSettings = settings
    }

    fun getAllParkingSpots(callback: (List<Parking>) -> Unit) {
        db.collection(PARKING_SPOTS_COLLECTION_PATH).orderBy("timestamp", Query.Direction.DESCENDING).get().addOnCompleteListener {
            when (it.isSuccessful) {
                true -> {
                    val parkingSpots: MutableList<Parking> = mutableListOf()
                    for (json in it.result) {
                        val parking = Parking.fromJSON(json.data)
                        parkingSpots.add(parking)
                    }
                    callback(parkingSpots)
                }
                false -> callback(listOf())
            }
        }
    }

    fun getAllParkingSpotsByUser(email: String, callback: (List<Parking>) -> Unit) {

        db.collection(PARKING_SPOTS_COLLECTION_PATH).whereEqualTo("owner", email).get().addOnCompleteListener {
            when(it.isSuccessful) {
                true -> {
                    val filteredPosts : MutableList<Parking> = mutableListOf()
                    for (json in it.result) {
                        val parking = Parking.fromJSON(json.data)
                        filteredPosts.add(parking)
                    }
                    callback(filteredPosts)
                }
                false -> {
                    callback(listOf())
                }
            }
        }
    }

    fun addParking(parking: Parking, callback: () -> Unit) {
        db.collection(PARKING_SPOTS_COLLECTION_PATH)
            .document(parking.address).set(parking.json).addOnSuccessListener {
                callback()
            }
    }

    fun deleteParking(parking: Parking, callback: () -> Unit) {
        db.collection(PARKING_SPOTS_COLLECTION_PATH)
            .document(parking.address).delete().addOnSuccessListener {
                callback()
            }
    }

    fun updateToUnavailable(parking: Parking, callback: () -> Unit) {
        db.collection(PARKING_SPOTS_COLLECTION_PATH)
            .document(parking.address).set(parking.json).addOnSuccessListener {
                callback()
            }
    }

    fun addProfile(profile: Profile, callback: () -> Unit) {
        db.collection(PROFILES_COLLECTION_PATH)
            .document(profile.email).set(profile.json).addOnSuccessListener {
                callback()
            }
    }

    fun updateUserDetails(email: String, profileDetails:  HashMap<String, Any>, callback: () -> Unit) {

        db.collection(PROFILES_COLLECTION_PATH)
            .document(email).update(profileDetails).addOnSuccessListener {
                callback()
            }
    }


    fun getUserByEmail(email: String, callback: (Profile?) -> Unit) {
        db.collection(PROFILES_COLLECTION_PATH)
            .document(email).get().addOnCompleteListener {

                when (it.isSuccessful) {
                    true -> {
                        val currProfile = it.result.data?.let { Profile.fromJSON(it) }
                        callback(currProfile)
                    }
                    false -> {
                        callback(null)
                    }
                }
            }
    }
}