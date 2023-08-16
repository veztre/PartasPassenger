package com.thesis.partas.passenger.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thesis.partas.passenger.R
import com.thesis.partas.passenger.model.BusSchedule
import com.thesis.partas.passenger.model.Reservation


class ReservationDataAdapter(private val reservationList: List<Reservation>) :
    RecyclerView.Adapter<ReservationDataAdapter.ReservationViewHolder>() {

    var onItemClick: ((Reservation) -> Unit)? = null

    class ReservationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
           // val busImage: ImageView = view.findViewById(R.id.imgBusType)
            val busRoute: TextView = view.findViewById(R.id.tvRoute)
            val seatCount: TextView =  view.findViewById(R.id.tvSeatCount)
            val scheduleTime: TextView = view.findViewById(R.id.tvSchedule)
            val totalPrice: TextView = view.findViewById(R.id.tvTotalPrice)

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.reservation_items, parent, false)
        return ReservationViewHolder(view)
    }




    override fun onBindViewHolder(viewHolder: ReservationViewHolder, position: Int) {
        val reservation= reservationList[position]
        val busRoute=reservation.formatRoute(reservation.origin,reservation.destination)
        val scheduleTime = reservation.formatSchedule(reservation.departure_time,reservation.arrival_time)

        // todo for image
        //viewHolder.busImage.getImageResource(schedule.imageType)

        viewHolder.busRoute.text = busRoute
        viewHolder.scheduleTime.text = scheduleTime
        viewHolder.seatCount.text = "Seats " + reservation.getNumberOfSeat(reservation.price,reservation.total_price)
        viewHolder.totalPrice.text ="Price "+ reservation.formatPrice(reservation.total_price)
        viewHolder.itemView.setOnClickListener(){
                onItemClick?.invoke(reservation)
        }


    }

    override fun getItemCount()= reservationList.size


}
