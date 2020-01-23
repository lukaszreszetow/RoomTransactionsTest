package com.example.roomtransactionstest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.roomtransactionstest.persistance.User
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private var lastJob: Job? = null
    
    private var jobCounter = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchOnce.setOnClickListener {
            Log.d("MainActivity", "Starting singular fetch")
            getUser()
        }

        fetchTwice.setOnClickListener {
            Log.d("MainActivity", "Starting double fetch")
            getUser()
            getUser()
        }

        lifecycleScope.launch {
            Database.db?.userDao()?.insert(User(Database.userId, Database.userName))
        }
    }

    private fun getUser() {
        lastJob?.cancel()
        
        val newJobCounter = jobCounter++

        Log.d("MainActivity", "[$currentThread] Starting job $newJobCounter")

        lastJob = lifecycleScope.launch {
            changeTexts("Job $newJobCounter - Loading data...", newJobCounter)

            try {
                Log.d("[$currentThread] Job $newJobCounter", "Getting user")
                val user = Database.db?.userDao()?.getUserWithTransaction(Database.userId)
                Log.d("[$currentThread] Job $newJobCounter", "Got user: $user")
                changeTexts("Job $newJobCounter - Data loaded: $user", newJobCounter)
            } catch (e: CancellationException) {
                Log.e("[$currentThread] Job $newJobCounter", "Getting user cancelled", e)
            } catch (e: Exception) {
                Log.e("[$currentThread] Job $newJobCounter", "Getting user failed", e)
                changeTexts("Job $newJobCounter - Data loading failed", newJobCounter)
            }
        }
    }

    private fun changeTexts(text: String, jobCounter: Int) {
        try {
            resultTV.text = text
        } catch (e: Exception) {
            Log.e("MainActivity", "Changing text failed (job: $jobCounter)", e)
        }
    }

}
