package com.example.roomtransactionstest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.roomtransactionstest.persistance.AppDatabase
import com.example.roomtransactionstest.persistance.User
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Database.db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()

        showFragment.setOnClickListener {
            val newFragment = SampleFragment()

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, newFragment)
                .commit()

            val newFragment2 = SampleFragment()

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, newFragment2)
                .commit()
        }

        lifecycleScope.launch(Dispatchers.Default) {
            Database.db?.userDao()?.insert(fakeUser)
        }
    }

    companion object {
        val fakeUser = User(123, "fakeName")
    }
}
