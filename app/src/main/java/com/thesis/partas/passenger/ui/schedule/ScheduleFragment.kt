package com.thesis.partas.passenger.ui.schedule

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thesis.partas.passenger.DetailActivity
import com.thesis.partas.passenger.adapter.ScheduleDataAdapter
import com.thesis.partas.passenger.databinding.FragmentScheduleBinding
import com.thesis.partas.passenger.model.BusSchedule


class ScheduleFragment : Fragment() {

    private var _binding: FragmentScheduleBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var scheduleData: List<BusSchedule> = listOf()
    private lateinit var txtData: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var scheduleDataAdapter: ScheduleDataAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recyclerView = binding.rcrSchedules
        txtData = binding.txtUser
        txtData.text = "Schedule List"

        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        recyclerView.hasFixedSize()
        val viewModel = ViewModelProvider(this).get(ScheduleViewModel::class.java)
        viewModel.getData()?.observe(viewLifecycleOwner) {
            scheduleData=it
            scheduleDataAdapter = ScheduleDataAdapter(scheduleData)
            recyclerView.adapter=scheduleDataAdapter
            scheduleDataAdapter.onItemClick={
                val intent = Intent(this.requireContext(),DetailActivity::class.java)
                intent.putExtra("schedule",it)
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