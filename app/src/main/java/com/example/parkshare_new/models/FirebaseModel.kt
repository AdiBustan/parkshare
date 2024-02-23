package com.example.parkshare_new.models

import android.location.Address
import android.util.Log
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.ktx.Firebase

class FirebaseModel {
    private val db = Firebase.firestore

    companion object {
        const val PARKING_LOTS_COLLECTION_PATH = "parkingLots"
    }

    init {
       val settings = firestoreSettings {
           setLocalCacheSettings(memoryCacheSettings {  })
       }
        db.firestoreSettings = settings
    }

    fun getAllParkingLots(callback: (List<Parking>) -> Unit) {
        db.collection(PARKING_LOTS_COLLECTION_PATH).orderBy("timestamp", Query.Direction.DESCENDING).get().addOnCompleteListener {
            when (it.isSuccessful) {
                true -> {
                    val parkingLots: MutableList<Parking> = mutableListOf()
                    for (json in it.result) {
                        val parking = Parking.fromJSON(json.data)
                        parkingLots.add(parking)
                    }
                    callback(parkingLots)
                }
                false -> callback(listOf())
            }
        }
    }

    fun getAllParkingLotsByUser(email: String, callback: (List<Parking>) -> Unit) {

        val query = db.collection(PARKING_LOTS_COLLECTION_PATH).whereEqualTo("owner", email)

        query.get()
            .addOnSuccessListener { querySnapshot ->
                val filteredPosts : MutableList<Parking> = mutableListOf()
                for (document in querySnapshot.documents) {
                    val post = document.toObject(Parking::class.java)!!
                    filteredPosts.add(post)
                }

            }
            .addOnFailureListener { exception ->
                // Handle errors
                Log.w("Firebase", "Error getting documents: $exception")
            }
    }

    fun addParking(parking: Parking, callback: () -> Unit) {
        db.collection(PARKING_LOTS_COLLECTION_PATH)
            .document(parking.address).set(parking.json).addOnSuccessListener {
                callback()
            }
    }

    fun deleteParking(parking: Parking, callback: () -> Unit) {
        db.collection(PARKING_LOTS_COLLECTION_PATH)
            .document(parking.address).delete().addOnSuccessListener {
                callback()
            }
    }

    fun updateToUnavailable(parking: Parking, callback: () -> Unit) {
        db.collection(PARKING_LOTS_COLLECTION_PATH)
            .document(parking.address).set(parking.json).addOnSuccessListener {
                callback()
            }
    }
}