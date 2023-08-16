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
import com.thesis.partas.passenger.model.Reservation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailReservationActivity : AppCompatActivity() {
    private lateinit var txtRoute: TextView
    private lateinit var txtSchedule: TextView
    private lateinit var txtTotalPrice: TextView
    private lateinit var txtNoSeat: TextView
    private lateinit var etRefNumber: EditText

    private lateinit var btnPay: Button
    private lateinit var btnResCancel: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_reservation)
        txtRoute = findViewById(R.id.txtResRoute)
        txtSchedule = findViewById(R.id.txtResSchedule)
        txtNoSeat = findViewById(R.id.txtNoSeat)
        txtTotalPrice = findViewById(R.id.txtTotPrice)
        etRefNumber = findViewById(R.id.etRefNumber)
        btnResCancel = findViewById(R.id.btnResCancel)
        btnPay = findViewById(R.id.btnPay)

        val reservationParcel = intent.getParcelableExtra<Reservation>("reservation")

        if (reservationParcel != null) {
            txtRoute.text = reservationParcel.formatRoute(
                reservationParcel.origin,
                reservationParcel.destination
            )
            txtSchedule.text = reservationParcel.formatSchedule(
                reservationParcel.departure_time,
                reservationParcel.arrival_time
            )
            txtNoSeat.text = reservationParcel.getNumberOfSeat(
                reservationParcel.price,
                reservationParcel.total_price
            )
            txtTotalPrice.text = reservationParcel.formatPrice(reservationParcel.total_price)

        }

        btnResCancel.setOnClickListener {
            //to do code for cancellation
            finish()
        }
        btnPay.setOnClickListener {
            val noReserve = etRefNumber.text
            payReservation(reservationParcel?.id,reservationParcel.total_price)

        }
    }
    private fun payReservation(reservation_id: Int?, totalprice: Float) {
            val sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
            val email = sharedPreferences.getString("email", "")

            val apiInterface = RetrofitHelper.getClient()?.create(ApiInterface::class.java)
            //Log.d("values",email.toString()+"-"+ reserve_count.toString() +"-"+schedule_id.toString())

            val call: Call<Reservation>? = apiInterface?.payReservation(email,reservation_id,totalprice)
            var payReservationData: Reservation?
            call!!.enqueue(object : Callback<Reservation?> {
                override fun onResponse(call: Call<Reservation?>, response: Response<Reservation?>) {
                    payReservationData = response.body()
                    if (response.isSuccessful) {
                        Log.d("hay naku", payReservationData.toString())
                        Toast.makeText(this@DetailReservationActivity, "Reservation is Successful", Toast.LENGTH_SHORT).show()
                        finish()
                    }

                }

                override fun onFailure(call: Call<Reservation?>, t: Throwable) {
                    Toast.makeText(this@DetailReservationActivity, t.toString(), Toast.LENGTH_SHORT).show()
                    Log.d("Error",t.toString())
                }

            })



    }
}