package com.example.parkshare_new.models

import android.os.Looper
import androidx.core.os.HandlerCompat
import com.example.parkshare_new.dao.AppLocalDatabase
import java.util.concurrent.Callable
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.security.auth.callback.Callback

class Model private constructor(){

    private val database = AppLocalDatabase.db
    private var executor = Executors.newSingleThreadExecutor()
    private var mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    private val firebaseModel = FirebaseModel()
    companion object {
        val instance: Model = Model()
    }

    fun getAllParkingLots(callback: (List<Parking>) -> Unit) {
        firebaseModel.getAllParkingLots(callback)
//        executor.execute {
//            Thread.sleep(5000) //TODO: delete line
//
//            val parkingLots = database.parkingDao().getAll()
//
//            mainHandler.post {
//                callback(parkingLots) // Back to the main thread
//
//            }
//        }
    }

    fun addParking(parking: Parking, callback: () -> Unit) {
        firebaseModel.addParking(parking, callback)
//        executor.execute {
//            database.parkingDao().insert(parking)
//            mainHandler.post {
//                callback()
//            }
//        }
    }
}