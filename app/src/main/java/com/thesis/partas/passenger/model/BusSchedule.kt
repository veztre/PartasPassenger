package com.thesis.partas.passenger.model

import android.os.Parcel
import android.os.Parcelable
import java.text.SimpleDateFormat

import java.text.NumberFormat
import java.util.Locale


data class BusSchedule(
    val id: Int,
    val code: String?,
    val origin: String?,
    val destination: String?,
    val departure_time: String?,
    val arrival_time: String?,
    val bus_type: String?,
    val price: Float
): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readFloat()
    ) {

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


    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        p0.writeInt(id)
        p0.writeString(code)
        p0.writeString(origin)
        p0.writeString(destination)
        p0.writeString(departure_time)
        p0.writeString(arrival_time)
        p0.writeString(bus_type)
        p0.writeFloat(price)
    }

    companion object CREATOR : Parcelable.Creator<BusSchedule> {
        override fun createFromParcel(parcel: Parcel): BusSchedule {
            return BusSchedule(parcel)
        }

        override fun newArray(size: Int): Array<BusSchedule?> {
            return arrayOfNulls(size)
        }
    }



}
