package com.example.roomtransactionstest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.roomtransactionstest.persistance.User
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var counter = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newFragmentOnce.setOnClickListener {
            replaceWithNewFragment()
        }

        newFragmentTwice.setOnClickListener {
            replaceWithNewFragment()
            replaceWithNewFragment()
        }

        lifecycleScope.launch(Dispatchers.IO) {
            Database.db?.userDao()?.insert(User(Database.userId, Database.userName))
        }
    }

    private fun replaceWithNewFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, getNewSampleFragment())
            .commit()
    }

    private fun getNewSampleFragment(): DataLoadingFragment {
        return DataLoadingFragment().apply {
            arguments = Bundle().apply {
                putInt(DataLoadingFragment.COUNTER, counter++)
            }
        }
    }
}
