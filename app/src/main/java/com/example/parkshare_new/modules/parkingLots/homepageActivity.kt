package com.example.parkshare_new.modules.parkingLots

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parkshare_new.R
import com.example.parkshare_new.models.Model
import com.example.parkshare_new.models.Parking
import com.example.parkshare_new.modules.parkingLots.adapter.ParkingLotsRecyclerAdapter

class homepageActivity : AppCompatActivity(){
    var parkingLotsRecyclerView: RecyclerView? = null
    var parkingLots: MutableList<Parking>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        parkingLots = Model.instance.parkingLots

        parkingLotsRecyclerView = findViewById(R.id.rvParkingLotsList)
        parkingLotsRecyclerView?.setHasFixedSize(true)

        //set the layout manager and adapter
        parkingLotsRecyclerView?.layoutManager = LinearLayoutManager(this)

        val adapter = ParkingLotsRecyclerAdapter(parkingLots)
        adapter.listener = object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                Log.i("TAG", "ParkingLotsRecyclerAdapter: position clicked on: $position")
            }

            override fun onParkingClicked(parking: Parking?) {
                Log.i("TAG", "PARKING: $parking")
            }
        }

        parkingLotsRecyclerView?.adapter = adapter
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)

        fun onParkingClicked(parking: Parking?)
    }




}