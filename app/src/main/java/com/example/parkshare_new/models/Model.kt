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
    companion object {
        val instance: Model = Model()
    }

    fun getAllParkingLots(callback: (List<Parking>) -> Unit) {
        executor.execute {
            Thread.sleep(5000) //TODO: delete line

            val parkingLots = database.studentDao().getAll()

            mainHandler.post {
                callback(parkingLots) // Back to the main thread

            }
        }
    }

    fun getAllParkingLotsPerUser(username: String, callback: (List<Parking>) -> Unit) {
        executor.execute {
            val parkingLots = database.studentDao().getParkingByUser(username)
            mainHandler.post {
                callback(listOf(parkingLots)) // Back to the main thread
            }
        }
    }

    fun addParking(parking: Parking, callback: () -> Unit) {
        executor.execute {
            database.studentDao().insert(parking)
            mainHandler.post {
                callback()
            }
        }
    }


}