package com.example.parkshare_new.models

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.ktx.Firebase

class FirebaseModel {
    private val db = Firebase.firestore

    init {
       val settings = firestoreSettings {
           setLocalCacheSettings(memoryCacheSettings {  })
       }
        db.firestoreSettings = settings
    }

    fun addDocument(collection: String, key: String, document:  Map<String, Any>, callback: () -> Unit) {
        db.collection(collection)
            .document(key).set(document).addOnSuccessListener {
                callback()
            }
    }

    fun deleteDocument(collection: String, key: String, callback: () -> Unit) {
        db.collection(collection)
            .document(key).delete().addOnSuccessListener {
                callback()
            }
    }

    fun updateDocument(collection: String, key: String, document:  Map<String, Any>, callback: () -> Unit) {
        db.collection(collection)
            .document(key).update(document).addOnSuccessListener {
                callback()
            }
    }

    fun getAllParkingSpots(callback: (List<Parking>) -> Unit) {
        db.collection(ParkingSpotModel.PARKING_SPOTS_COLLECTION_PATH)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get().addOnCompleteListener {
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
        db.collection(ParkingSpotModel.PARKING_SPOTS_COLLECTION_PATH)
            .whereEqualTo("owner", email)
            .get().addOnCompleteListener {
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

    fun getUserByEmail(email: String, callback: (Profile?) -> Unit) {
        db.collection(UserModel.PROFILES_COLLECTION_PATH)
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