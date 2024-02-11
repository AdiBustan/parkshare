package com.example.parkshare_new.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.parkshare_new.models.Parking

@Dao
interface ParkingDao {

    @Query("SELECT * FROM Parking")
    fun getAll(): List<Parking>

    @Query("SELECT * FROM Parking " +
            "WHERE address=:address")
    fun getParkingById(address: String) : Parking

    @Query("SELECT * FROM Parking " +
            "WHERE address LIKE :address AND city LIKE :city")
    fun getParkingByAddressAndCity(address: String, city: String) : Parking

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg parkingLots: Parking)

    @Delete
    fun delete(parking: Parking)
}