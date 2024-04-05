package com.example.parkshare_new.models

import android.location.Address
import android.os.Looper
import android.util.Log
import androidx.core.os.HandlerCompat
//import com.example.parkshare_new.dao.AppLocalDatabase
import java.util.concurrent.Callable
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.security.auth.callback.Callback

class Model private constructor(){

    private val firebaseModel = FirebaseModel()

    companion object {
        val instance: Model = Model()
    }

    fun getAllParkingSpots(callback: (List<Parking>) -> Unit) {
        firebaseModel.getAllParkingSpots(callback)
    }

    fun getAllParkingSpotsPerUser(email: String, callback: (List<Parking>) -> Unit) {
        firebaseModel.getAllParkingSpotsByUser(email, callback)
    }

    fun addUser(profile: Profile, callback: () -> Unit) {
        firebaseModel.addProfile(profile, callback)
    }

    fun updateUserDetails(profile: Profile, callback: () -> Unit) {
        val updatedData = hashMapOf<String, Any>(
            "userName" to profile.userName,
            "faveCity" to profile.faveCity,
            "avatar" to profile.avatar
        )

        firebaseModel.updateUserDetails(profile.email, updatedData, callback)
    }

    fun getUserByEmail(email: String, callback: (Profile?) -> Unit) {
        firebaseModel.getUserByEmail(email, callback)
    }

    fun addParking(parking: Parking, callback: () -> Unit) {
        firebaseModel.addParking(parking, callback)
    }

    fun deleteParking(parking: Parking, callback: () -> Unit) {
        firebaseModel.deleteParking(parking, callback)
    }

    fun updateToUnavailable(parking: Parking, callback: () -> Unit) {
        firebaseModel.updateToUnavailable(parking, callback)

    }
}