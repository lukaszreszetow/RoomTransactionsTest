package com.example.roomtransactionstest.persistance

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val uid: Int,
    val firstName: String
)
