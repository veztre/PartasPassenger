package com.thesis.partas.passenger.ui.reservation

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thesis.partas.passenger.DetailActivity
import com.thesis.partas.passenger.DetailReservationActivity
import com.thesis.partas.passenger.adapter.ReservationDataAdapter
import com.thesis.partas.passenger.adapter.ScheduleDataAdapter
import com.thesis.partas.passenger.databinding.FragmentReservationBinding
import com.thesis.partas.passenger.ui.schedule.ScheduleViewModel


class ReservationFragment : Fragment() {
    private lateinit var txtData: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var reservationDataAdapter: ReservationDataAdapter

    private var _binding: FragmentReservationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val reservationViewModel =
            ViewModelProvider(this).get(ReservationViewModel::class.java)

        _binding = FragmentReservationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val sharedPreferences: SharedPreferences = this.requireContext().getSharedPreferences("user", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email", "")



        recyclerView = binding.rcrReservations
        txtData = binding.tvReservation
        txtData.text = "My Reservation List"

        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())

        reservationViewModel.getReservation(email).observe(viewLifecycleOwner) {
            Log.d("reservation count",it.size.toString())
            reservationDataAdapter = ReservationDataAdapter(it)
            recyclerView.adapter=reservationDataAdapter
            reservationDataAdapter.onItemClick={
                val intent = Intent(this.requireContext(), DetailReservationActivity::class.java)
                intent.putExtra("reservation",it)
                startActivity(intent)
            }

        }


        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}