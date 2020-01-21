package com.example.roomtransactionstest.persistance

import android.util.Log
import androidx.room.*
import com.example.roomtransactionstest.User2
import kotlinx.coroutines.delay

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE uid = :userId")
    suspend fun getUser(userId: Int): User?

    @Transaction
    suspend fun getUser2(userId: Int): User2? {
        Log.d("UserDao", "Starting getUser2")
        delay(3000)
        return getUser(userId)?.toUser2()
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(user: User)
}