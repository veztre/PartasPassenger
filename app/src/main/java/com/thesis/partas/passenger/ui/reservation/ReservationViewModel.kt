package com.thesis.partas.passenger.ui.reservation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.thesis.partas.passenger.model.Reservation


class ReservationViewModel : ViewModel() {
    private lateinit  var repository: ReservationRepository
    lateinit var data: LiveData<List<Reservation>>

    fun getReservation(email: String?): LiveData<List<Reservation>> {
        repository = ReservationRepository()
        data = repository.fetchData(email)
        return data
    }
}