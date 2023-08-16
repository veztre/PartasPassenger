package com.thesis.partas.passenger

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.partas.dbConn.RetrofitHelper
import com.thesis.partas.passenger.api.ApiInterface

import com.thesis.partas.passenger.model.BusSchedule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    private lateinit var txtRoute: TextView
    private lateinit var txtSchedule: TextView
    private lateinit var txtCode: TextView
    private lateinit var txtPrice: TextView
    private lateinit var etCountReserve: EditText

    private lateinit var btnReserve: Button
    private lateinit var btnCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val scheduleParcel = intent.getParcelableExtra<BusSchedule>("schedule")
        txtRoute= findViewById(R.id.txtRoute)
        txtSchedule=findViewById(R.id.txtSchedule)
        txtCode = findViewById(R.id.txtCode)
        txtPrice=findViewById(R.id.txtPrice)
        etCountReserve=findViewById(R.id.etSeatCount)

        btnCancel = findViewById(R.id.btnCancel)
        btnReserve = findViewById(R.id.btnReserve)

        if (scheduleParcel != null) {
            txtRoute.text = scheduleParcel.formatRoute(scheduleParcel.origin,scheduleParcel.destination)
            txtSchedule.text= scheduleParcel.formatSchedule(scheduleParcel.departure_time,scheduleParcel.arrival_time)
            txtCode.text=scheduleParcel.code
            txtPrice.text=scheduleParcel.formatPrice(scheduleParcel.price)

        }
       btnCancel.setOnClickListener{
            finish()
        }
        btnReserve.setOnClickListener{
           val noReserve = etCountReserve.text
           saveReservation(scheduleParcel?.id,noReserve.toString())
        }

    }
    private fun saveReservation(schedule_id: Int?, reserve:String) {
        val sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email", "")

        val apiInterface = RetrofitHelper.getClient()?.create(ApiInterface::class.java)
        val reserve_count = Integer.parseInt(reserve)

        Log.d("values",email.toString()+"-"+ reserve_count.toString() +"-"+schedule_id.toString())

        val call: Call<BusSchedule>? = apiInterface?.reserveSchedule(email,schedule_id,reserve_count)
        var reserveData: BusSchedule?
        call!!.enqueue(object : Callback<BusSchedule?> {
            override fun onResponse(call: Call<BusSchedule?>, response: Response<BusSchedule?>) {
                reserveData = response.body()
                if (response.isSuccessful) {
                    Log.d("hay naku", reserveData.toString())
                    Toast.makeText(this@DetailActivity, "Reservation is Successful", Toast.LENGTH_SHORT).show()
                    finish()
                }

            }

            override fun onFailure(call: Call<BusSchedule?>, t: Throwable) {
                Toast.makeText(this@DetailActivity, t.toString(), Toast.LENGTH_SHORT).show()
                Log.d("Error",t.toString())
            }

        })
        Log.d( "Current User", email.toString())


    }



}