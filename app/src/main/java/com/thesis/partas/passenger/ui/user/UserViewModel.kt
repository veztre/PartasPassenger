package com.thesis.partas.passenger.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.thesis.partas.passenger.model.User


class UserViewModel : ViewModel() {
    private lateinit  var repository: UserRepository
    private lateinit var data: LiveData<User>

    fun getData(): LiveData<User>? {
        repository = UserRepository()
        data = repository!!.fetchData()
        return data
    }
}