package com.thesis.partas.passenger.ui.login


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.partas.dbConn.RetrofitHelper
import com.thesis.partas.passenger.api.ApiInterface
import com.thesis.partas.passenger.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response




class LoginRepository {

    private val apiInterface: ApiInterface = RetrofitHelper.getInstance().create(ApiInterface::class.java)


    fun performLogin(email: String, password: String): LiveData<User> {
            val currentUser: MutableLiveData<User> = MutableLiveData<User>()
             val call = apiInterface.login(email,password)
            call!!.enqueue(object : Callback<User> {
                override fun onResponse(
                    call: Call<User>,
                    response: Response<User>
             ) {
                    if (response.isSuccessful) {
                       currentUser.value = response.body()
                        Log.d("current user", response.body().toString())
                    } else{

                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable?) {

                }


            })
            return currentUser
        }


}