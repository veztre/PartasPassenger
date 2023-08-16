package com.thesis.partas.passenger.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thesis.partas.passenger.R
import com.thesis.partas.passenger.model.BusSchedule


class ScheduleDataAdapter(private val scheduleList: List<BusSchedule>) :
    RecyclerView.Adapter<ScheduleDataAdapter.ScheduleViewHolder>() {

    var onItemClick: ((BusSchedule) -> Unit)? = null

    class ScheduleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
           // val busImage: ImageView = view.findViewById(R.id.imgBusType)
            val busRoute: TextView = view.findViewById(R.id.tvRoute)
            val busCode: TextView =  view.findViewById(R.id.tvBusCode)
            val scheduleTime: TextView = view.findViewById(R.id.tvSchedule)
            val price: TextView = view.findViewById(R.id.tvPrice)

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.schedule_items, parent, false)
        return ScheduleViewHolder(view)
    }




    override fun onBindViewHolder(viewHolder: ScheduleViewHolder, position: Int) {
        val schedule= scheduleList[position]
        val busRoute=schedule.formatRoute(schedule.origin,schedule.destination)
        val scheduleTime = schedule.formatSchedule(schedule.departure_time,schedule.arrival_time)

        // todo for image
        //viewHolder.busImage.getImageResource(schedule.imageType)

        viewHolder.busRoute.text = busRoute
        viewHolder.busCode.text = schedule.code
        viewHolder.scheduleTime.text = scheduleTime
        viewHolder.price.text = schedule.price.toString()
        viewHolder.itemView.setOnClickListener(){
                onItemClick?.invoke(schedule)
        }


    }

    override fun getItemCount()= scheduleList.size


}
