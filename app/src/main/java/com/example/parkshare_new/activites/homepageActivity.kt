package com.example.parkshare_new.activites

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
import com.google.firebase.FirebaseApp

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

        val adapter = ParkingLotsRecyclerAdapter()
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

    inner class ParkingLotsViewHolder(val itemView: View, val listener: OnItemClickListener?): RecyclerView.ViewHolder(itemView) {


        var parkingAddressTextView : TextView? = null
        var parkingCityTextView: TextView? = null
        var parkingCheckBox: CheckBox? = null
        var parking: Parking? = null
        init {
            parkingAddressTextView = itemView.findViewById(R.id.tvParkingAdress)
            parkingCityTextView = itemView.findViewById(R.id.tvParkingCity)
            parkingCheckBox = itemView.findViewById(R.id.cbAvalibleParking)

            parkingCheckBox?.setOnClickListener {
                var parking = parkingLots?.get(adapterPosition)
                parking?.isChecked = parkingCheckBox?.isChecked ?: false
            }

            itemView.setOnClickListener{
                Log.i("TAG", "ParkingLotsViewHolder: position clicked: $adapterPosition")
                listener?.onItemClick(adapterPosition)
                listener?.onParkingClicked(parking)
            }
        }

        fun bind(parking: Parking?) {
            this.parking = parking
            parkingAddressTextView?.text = parking?.address
            parkingCityTextView?.text = parking?.city
            parkingCheckBox?.apply {
                isChecked = parking?.isChecked ?: false
            }
        }
    }

    inner class ParkingLotsRecyclerAdapter: RecyclerView.Adapter<ParkingLotsViewHolder> () {
        var listener: OnItemClickListener? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkingLotsViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.parking_list_row, parent, false)
            return ParkingLotsViewHolder(itemView, listener)
        }

        override fun getItemCount(): Int {
            return parkingLots?.size ?: 0
        }

        override fun onBindViewHolder(holder: ParkingLotsViewHolder, position: Int) {
            val parking = parkingLots?.get(position)
            holder.bind(parking)

        }

    }
}