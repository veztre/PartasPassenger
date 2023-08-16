package com.thesis.partas.passenger.ui.schedule


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.partas.dbConn.RetrofitHelper
import com.thesis.partas.passenger.api.ApiInterface
import com.thesis.partas.passenger.model.BusSchedule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ScheduleRepository {

    private val apiInterface: ApiInterface = RetrofitHelper.getInstance().create(ApiInterface::class.java)

    fun fetchData(): LiveData<List<BusSchedule>> {
            val data: MutableLiveData<List<BusSchedule>> = MutableLiveData<List<BusSchedule>>()

            val call: Call<List<BusSchedule>> = apiInterface.getSchedules()
            call.enqueue(object : Callback<List<BusSchedule>> {
                override fun onResponse(
                    call: Call<List<BusSchedule>?>?,
                    response: Response<List<BusSchedule>?>

                ) {
                    if (response.isSuccessful) {
                        data.setValue(response.body())
                    } else {
                        Log.d("Error here",response.toString())
                    }
                }

                override fun onFailure(call: Call<List<BusSchedule>>, t: Throwable?) {
                }
            })
            return data
        }


}