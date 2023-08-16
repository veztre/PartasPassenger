package com.thesis.partas.passenger.ui.user


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.partas.dbConn.RetrofitHelper
import com.thesis.partas.passenger.api.ApiInterface
import com.thesis.partas.passenger.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserRepository {

    private val apiInterface: ApiInterface = RetrofitHelper.getInstance().create(ApiInterface::class.java)

    fun fetchData(): MutableLiveData<User> {
            val data: MutableLiveData<User> = MutableLiveData<User>()
            val call: Call<User> = apiInterface.getUser()
            call.enqueue(object : Callback<User> {
                override fun onResponse(
                    call: Call<User>,
                    response: Response<User>

                ) {
                    if (response.isSuccessful) {
                        data.setValue(response.body())
                    } else {
                        Log.d("Error here",response.toString())
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable?) {
                }
            })
            return data
        }


}