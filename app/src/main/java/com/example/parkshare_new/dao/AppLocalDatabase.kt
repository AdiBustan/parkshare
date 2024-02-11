package com.example.parkshare_new.dao

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.parkshare_new.base.MyApplication
import com.example.parkshare_new.models.Parking

@Database(entities = [Parking::class], version = 1)
abstract class AppLocalDbRepository : RoomDatabase() {
    abstract fun studentDao(): ParkingDao
}

object AppLocalDatabase {

    val db: AppLocalDbRepository by lazy {

        val context = MyApplication.Globals.appContext
            ?: throw IllegalStateException("Application context not available")

        Room.databaseBuilder(
            context,
            AppLocalDbRepository::class.java,
            "dbFileName.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

}