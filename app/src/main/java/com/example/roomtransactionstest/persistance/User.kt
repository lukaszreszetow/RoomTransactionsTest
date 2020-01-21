package com.example.roomtransactionstest.persistance

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.roomtransactionstest.User2

@Entity
data class User(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "first_name") val firstName: String
) {
    fun toUser2(): User2 {
        return User2(uid.toString(), firstName)
    }
}