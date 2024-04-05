package com.example.parkshare_new.modules.parkingSpots

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parkshare_new.R
import com.example.parkshare_new.HomepageActivity
import com.example.parkshare_new.databinding.FragmentParkingSpotsBinding
import com.example.parkshare_new.models.Model
import com.example.parkshare_new.models.Parking
import com.example.parkshare_new.modules.parkingSpots.adapter.ParkingSpotsRecyclerAdapter

class ParkingSpotsFragment : Fragment() {
    var parkingSpotsRecyclerView: RecyclerView? = null
    var progressBar: ProgressBar? = null
    var noParkingsTextView: TextView? = null
    var parkingSpots: List<Parking>? = null
    var adapter : ParkingSpotsRecyclerAdapter? = null

    private var _binding: FragmentParkingSpotsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentParkingSpotsBinding.inflate(inflater, container, false)
        val view = binding.root

        adapter = ParkingSpotsRecyclerAdapter(parkingSpots)

        noParkingsTextView = binding.tvNoParkingParkingSpotsFragment
        progressBar = binding.progressBar //view.findViewById(R.id.progressBar)
        progressBar?.visibility = View.VISIBLE


        Model.instance.getAllParkingSpots { parkingSpots ->
            this.parkingSpots = parkingSpots
            adapter?.parkingSpots = parkingSpots
            adapter?.notifyDataSetChanged()

            updateEmptyViewVisibility()
            progressBar?.visibility = View.GONE
        }

        parkingSpotsRecyclerView = binding.rvParkingSpotsFragmentList
        parkingSpotsRecyclerView?.setHasFixedSize(true)

        //set the layout manager and adapter
        parkingSpotsRecyclerView?.layoutManager = LinearLayoutManager(context)

        adapter?.homepageListener = object : HomepageActivity.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val parking = parkingSpots?.get(position)
                parking?.let {
                    val action = ParkingSpotsFragmentDirections.actionParkingSpotsFragmentToParkingFragment(it.address, it.city, it.avatar)
                    Navigation.findNavController(view).navigate(action)
                }
            }

            override fun onParkingClicked(parking: Parking?) {
                Log.i("TAG", "PARKING: $parking")
            }
        }

        parkingSpotsRecyclerView?.adapter = adapter

        val addParkingButton: ImageButton = binding.btnParkingSpotsFragmentAdd
        val actionAdd = Navigation.createNavigateOnClickListener(R.id.action_parkingSpotsFragment_to_addParkingFragment)
        addParkingButton.setOnClickListener(actionAdd)

        return view
    }

    private fun updateEmptyViewVisibility() {
        if (this.parkingSpots?.isEmpty() == true) {
            noParkingsTextView?.visibility = View.VISIBLE
        } else {
            noParkingsTextView?.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()

        Model.instance.getAllParkingSpots { parkingSpots ->
            this.parkingSpots = parkingSpots
            adapter?.parkingSpots = parkingSpots
            adapter?.notifyDataSetChanged()

            updateEmptyViewVisibility()
            progressBar?.visibility = View.GONE
        }
    }
}