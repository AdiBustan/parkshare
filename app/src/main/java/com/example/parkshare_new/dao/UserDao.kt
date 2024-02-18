package com.example.parkshare_new.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.parkshare_new.models.LocalUser

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: LocalUser)

    @Update
    fun updateUser(user: LocalUser)

    @Delete
    fun deleteUser(user: LocalUser)

    @Query("SELECT * FROM user ORDER BY timestamp DESC LIMIT 1")
    fun getUser(): LocalUser

    @Query("SELECT * FROM user WHERE email = :userId")
    fun getUserById(userId: Long): LocalUser
}