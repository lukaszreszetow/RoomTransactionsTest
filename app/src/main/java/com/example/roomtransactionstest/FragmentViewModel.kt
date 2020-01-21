package com.example.roomtransactionstest

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FragmentViewModel : ViewModel() {

    fun fetchUser() = viewModelScope.launch {
        Log.d("FragmentViewModel", "Before fetching user")
        val user = Database.db?.userDao()?.getUser2(MainActivity.fakeUser.uid)
        Log.d("FragmentViewModel", "After fetching user: $user")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("FragmentViewModel", "onCleared function called")
    }

}