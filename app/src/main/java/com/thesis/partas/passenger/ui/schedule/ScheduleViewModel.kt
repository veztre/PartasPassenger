package com.thesis.partas.passenger.ui.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.thesis.partas.passenger.model.BusSchedule


class ScheduleViewModel : ViewModel() {
    private lateinit  var repository: ScheduleRepository
    private lateinit var data: LiveData<List<BusSchedule>>

    fun getData(): LiveData<List<BusSchedule>>? {
        repository = ScheduleRepository()
        data = repository!!.fetchData()
        return data
    }
}