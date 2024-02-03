package com.example.parkshare_new.modules.parkingLots

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parkshare_new.R
import com.example.parkshare_new.models.Model
import com.example.parkshare_new.models.Parking
import com.example.parkshare_new.modules.parkingLots.adapter.ParkingLotsRecyclerAdapter

class ParkingLotsFragment : Fragment() {
    var parkingLotsRecyclerView: RecyclerView? = null
    var parkingLots: MutableList<Parking>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_parking_lots, container, false)

        parkingLots = Model.instance.parkingLots

        parkingLotsRecyclerView = view.findViewById(R.id.rvParkingLotsFragmentList)
        parkingLotsRecyclerView?.setHasFixedSize(true)

        //set the layout manager and adapter
        parkingLotsRecyclerView?.layoutManager = LinearLayoutManager(context)

        val adapter = ParkingLotsRecyclerAdapter(parkingLots)
        adapter.listener = object : homepageActivity.OnItemClickListener {
            override fun onItemClick(position: Int) {
                Log.i("TAG", "ParkingLotsRecyclerAdapter: position clicked on: $position")
            }

            override fun onParkingClicked(parking: Parking?) {
                Log.i("TAG", "PARKING: $parking")
            }
        }

        parkingLotsRecyclerView?.adapter = adapter

        return view
    }
}