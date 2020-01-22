package com.example.roomtransactionstest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.fragment_layout.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataLoadingFragment : Fragment() {

    private val fragmentNumber by lazy { arguments?.getInt(COUNTER) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentCounter.text = "Fragment $fragmentNumber"

        viewLifecycleOwner.lifecycleScope.launch {
            changeText("Loading data...")

            try {
                Log.d("SampleFragment $fragmentNumber", "Getting user")

                val user = withContext(Dispatchers.Main) {
                    // Change to Dispatchers.IO to fix the deadlock
                    Database.db?.userDao()?.getUserWithTransaction(Database.userId)
                }

                Log.d("SampleFragment $fragmentNumber", "Got user: $user")
                changeText("Data loaded: $user")
            } catch (e: CancellationException) {
                Log.e("SampleFragment $fragmentNumber", "Getting user cancelled", e)
                changeText("Data loading cancelled")
            } catch (e: Exception) {
                Log.e("SampleFragment $fragmentNumber", "Getting user failed", e)
                changeText("Data loading failed")
            }
        }
    }

    private fun changeText(text: String) {
        try {
            fragmentResult.text = text
        } catch (e: Exception) {
            Log.e("SampleFragment $fragmentNumber", "Changing text failed", e)
        }
    }

    companion object {
        const val COUNTER = "COUNTER"
    }
}