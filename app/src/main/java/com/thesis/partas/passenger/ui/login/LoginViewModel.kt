package com.thesis.partas.passenger.ui.login

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.thesis.partas.passenger.model.User


class LoginViewModel : ViewModel() {
    private lateinit  var repository: LoginRepository
    var currentUser: LiveData<User>? = null

    fun performLogin(email: String, password: String): LiveData<User>? {
        repository = LoginRepository()
        currentUser = repository.performLogin(email,password)
        Log.d("model user",currentUser.toString())
        return currentUser
    }
}