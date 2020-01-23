package com.example.roomtransactionstest.persistance

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.delay

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(user: User)

    @Query("SELECT * FROM user WHERE uid = :userId")
    suspend fun getUser(userId: Int): User?

    @Transaction
    suspend fun getUserWithTransaction(userId: Int): User? {
        Log.d("UserDao", "getUserWithTransaction - start delay")
        delay(3000)
        Log.d("UserDao", "getUserWithTransaction - after delay")
        return getUser(userId)
    }
}