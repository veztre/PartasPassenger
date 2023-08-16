package com.thesis.partas.passenger.model

import android.os.Parcel
import android.os.Parcelable
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

data class Reservation(

    val email: String?,
    val schedule_id: Int,
    val origin: String?,
    val destination: String?,
    val departure_time: String?,
    val arrival_time: String?,
    val price: Float,
    val seat_count: Int,
    val total_price: Float

): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readFloat()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(email)
        parcel.writeInt(schedule_id)
        parcel.writeString(origin)
        parcel.writeString(destination)
        parcel.writeString(departure_time)
        parcel.writeString(arrival_time)
        parcel.writeFloat(price)
        parcel.writeInt(seat_count)
        parcel.writeFloat(total_price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Reservation> {
        override fun createFromParcel(parcel: Parcel): Reservation {
            return Reservation(parcel)
        }

        override fun newArray(size: Int): Array<Reservation?> {
            return arrayOfNulls(size)
        }
    }
    fun formatSchedule(departure_time: String?, arrival_time: String?): String{
        val pattern = "MM-dd-yyyy hh:mm a"
        val initialPattern =  "yyyy-MM-dd HH:mm:ss"
        val initPattern = SimpleDateFormat(initialPattern)
        val formattedPattern = SimpleDateFormat(pattern)
        //convert string parameter to date
        val arrivalTime = initPattern.parse(departure_time)
        val departureTime = initPattern.parse(arrival_time)
        //convert the date to specified format "MM-dd-YYYY HH:mm a"
        val formattedArrivalTime = formattedPattern.format(arrivalTime)
        val formattedDepartureTime = formattedPattern.format(departureTime)
        return "$formattedDepartureTime - $formattedArrivalTime"
    }

    fun formatRoute(origin: String?, destination: String?): String{
        return "$origin - $destination"
    }

    fun formatPrice(price: Float): String {
        val currencyFormat = NumberFormat.getCurrencyInstance(Locale("fil", "PH"))
        return currencyFormat.format(price)
    }
    fun getNumberOfSeat(price: Float,total_price: Float): String {

        return (total_price/price).toString()
    }

}
