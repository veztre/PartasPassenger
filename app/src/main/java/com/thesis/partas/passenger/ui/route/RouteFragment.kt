package com.thesis.partas.passenger.ui.route


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.partas.dbConn.RetrofitHelper
import com.thesis.partas.passenger.api.ApiInterface
import com.thesis.partas.passenger.databinding.FragmentRouteBinding
import com.thesis.partas.passenger.model.Location
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RouteFragment : Fragment(){
    private lateinit var sprOrigin: Spinner
    private lateinit var sprDestination: Spinner
    private  var destination: String? = null
    private var origin: String? = null

    private var _binding: FragmentRouteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRouteBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val testButton: Button = binding.testButton
        sprOrigin =binding.sprOrigin
        sprDestination=binding.sprDestination

        fetchLocationsFromServer()

        testButton.setOnClickListener {
            if (destination==origin)
                Toast.makeText(this.requireContext(), " same $destination $origin", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this.requireContext(), "not same$origin $destination", Toast.LENGTH_SHORT).show()
        }

        return root
    }


    private fun fetchLocationsFromServer() {
        val locations = RetrofitHelper.getClient()?.create(ApiInterface::class.java)
        val call: Call<List<Location>>? = locations?.getLocations()
        var locationsFromAPI: List<Location>?
        call!!.enqueue(object : Callback<List<Location>?> {
            override fun onResponse(call: Call<List<Location>?>, response: Response<List<Location>?>) {
                locationsFromAPI = response.body() ?: emptyList()
                if(response.isSuccessful){
                    populateSpinner(locationsFromAPI!!)
                    populateSpinner(locationsFromAPI!!)
                }

            }
            override fun onFailure(call: Call<List<Location>?>, t: Throwable) {
                Toast.makeText(this@RouteFragment.requireContext(), t.toString(), Toast.LENGTH_SHORT).show()
            }
        })

    }



    private fun populateSpinner(locationFromAPI: List<Location>) {
        val itemNames = locationFromAPI.map { it.location }
        val spinnerAdapter = ArrayAdapter(this@RouteFragment.requireContext(), android.R.layout.simple_spinner_item, itemNames)
        sprOrigin.adapter = spinnerAdapter
        sprDestination.adapter = spinnerAdapter

        sprOrigin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                origin = itemNames[position].toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        sprDestination.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                destination = itemNames[position].toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }


        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

