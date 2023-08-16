package com.thesis.partas.passenger.ui.reservation


import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.partas.dbConn.RetrofitHelper
import com.thesis.partas.passenger.api.ApiInterface
import com.thesis.partas.passenger.model.Reservation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class ReservationRepository {

    private val apiInterface: ApiInterface = RetrofitHelper.getInstance().create(ApiInterface::class.java)
     fun fetchData(email: String?): LiveData<List<Reservation>> {
            Log.d("nakarating ba dito",email.toString())
            val data: MutableLiveData<List<Reservation>> = MutableLiveData<List<Reservation>>()
            val call: Call<List<Reservation>> = apiInterface.getReservations(email!!)
            call.enqueue(object : Callback<List<Reservation>> {
                override fun onResponse(
                    call: Call<List<Reservation>?>?,
                    response: Response<List<Reservation>?>

                ) {
                    if (response.isSuccessful) {
                        data.setValue(response.body())
                        Log.d("Network Error here",data.toString())
                    } else {
                        Log.d("Error here",response.toString())
                    }
                }

                override fun onFailure(call: Call<List<Reservation>>, t: Throwable?) {
                    Log.d("Network Error here",t.toString())
                }
            })
            return data
        }

   

}