package com.example.parkshare_new.modules.parkingLots

import android.os.Bundle
import android.util.Log
import android.view.Display.Mode
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parkshare_new.R
import com.example.parkshare_new.HomepageActivity
import com.example.parkshare_new.databinding.FragmentParkingBinding
import com.example.parkshare_new.databinding.FragmentParkingLotsBinding
import com.example.parkshare_new.models.Model
import com.example.parkshare_new.models.Parking
import com.example.parkshare_new.modules.parkingLots.adapter.ParkingLotsRecyclerAdapter

class ParkingLotsFragment : Fragment() {
    var parkingLotsRecyclerView: RecyclerView? = null
    var parkingLots: List<Parking>? = null
    var adapter : ParkingLotsRecyclerAdapter? = null
    var progressBar: ProgressBar? = null

    private var _binding: FragmentParkingLotsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentParkingLotsBinding.inflate(inflater, container, false)
        val view = binding.root

        adapter = ParkingLotsRecyclerAdapter(parkingLots)

        progressBar = binding.progressBar //view.findViewById(R.id.progressBar)
        progressBar?.visibility = View.VISIBLE

        Model.instance.getAllParkingLots { parkingLots ->
            this.parkingLots = parkingLots
            adapter?.parkingLots = parkingLots
            adapter?.notifyDataSetChanged()

            progressBar?.visibility = View.GONE
        }

        parkingLotsRecyclerView = binding.rvParkingLotsFragmentList //view.findViewById(R.id.rvParkingLotsFragmentList)
        parkingLotsRecyclerView?.setHasFixedSize(true)

        //set the layout manager and adapter
        parkingLotsRecyclerView?.layoutManager = LinearLayoutManager(context)


        adapter?.listener = object : HomepageActivity.OnItemClickListener {
            override fun onItemClick(position: Int) {
                Log.i("TAG", "ParkingLotsRecyclerAdapter: position clicked on: $position")
                val parking = parkingLots?.get(position)
                parking?.let {
                    val action = ParkingLotsFragmentDirections.actionParkingLotsFragmentToParkingFragment(it.address)
                    Navigation.findNavController(view).navigate(action)
                }
            }

            override fun onParkingClicked(parking: Parking?) {
                Log.i("TAG", "PARKING: $parking")
            }
        }

        parkingLotsRecyclerView?.adapter = adapter

        val addParkingButton: ImageButton = binding.btnParkingLotsFragmentAdd //view.findViewById(R.id.btnParkingLotsFragmentAdd)
        val actionAdd = Navigation.createNavigateOnClickListener(R.id.action_parkingLotsFragment_to_addParkingFragment)
        addParkingButton.setOnClickListener(actionAdd)

        return view
    }

    override fun onResume() {
        super.onResume()

        Model.instance.getAllParkingLots { parkingLots ->
            this.parkingLots = parkingLots
            adapter?.parkingLots = parkingLots
            adapter?.notifyDataSetChanged()

            progressBar?.visibility = View.GONE
        }
    }
}