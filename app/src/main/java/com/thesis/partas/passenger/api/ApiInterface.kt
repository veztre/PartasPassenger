package com.thesis.partas.passenger.api


import com.thesis.partas.passenger.model.Location
import com.thesis.partas.passenger.model.BusSchedule
import com.thesis.partas.passenger.model.Reservation
import com.thesis.partas.passenger.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiInterface {

    @GET("locations")
    fun getLocations():
            Call <List<Location>>

    @GET("schedules")
   fun getSchedules():
            Call <List<BusSchedule>>

    @GET("user/{email}")
    fun getUser():
            Call <User>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<User>?


    @POST("saveUser")
    fun saveUser(@Body user: User):
            Call <User>

    @FormUrlEncoded
    @POST("reserve_schedule")
    fun reserveSchedule(
        @Field("email") email: String?,
        @Field("schedule_id") schedule_id: Int?,
        @Field("reserve_count") reserve_count: Int?,

     ): Call<BusSchedule>?

    @FormUrlEncoded
    @POST("reservations")
    fun getReservations(@Field("email") email: String):
            Call <List<Reservation>>

    @FormUrlEncoded
    @POST("pay_reservation")
    fun payReservation(@Field("email") email: String?,
                       @Field("reservationId") reservationId: Int?,
                       @Field("totalprice") totalprice: Float):
            Call<Reservation>?


}