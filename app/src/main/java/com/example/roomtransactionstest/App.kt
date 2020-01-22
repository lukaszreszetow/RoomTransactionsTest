package com.example.roomtransactionstest

import android.app.Application
import androidx.room.Room
import com.example.roomtransactionstest.persistance.AppDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Database.db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()
    }
}