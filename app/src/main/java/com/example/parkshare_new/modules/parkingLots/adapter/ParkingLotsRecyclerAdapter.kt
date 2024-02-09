package com.example.parkshare_new.modules.parkingLots.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.parkshare_new.R
import com.example.parkshare_new.HomepageActivity
import com.example.parkshare_new.models.Parking

class ParkingLotsRecyclerAdapter(var parkingLots: MutableList<Parking>?): RecyclerView.Adapter<ParkingLotsViewHolder> () {
    var listener: HomepageActivity.OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkingLotsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.parking_list_row, parent, false)
        return ParkingLotsViewHolder(itemView, listener, parkingLots)
    }

    override fun getItemCount(): Int {
        return parkingLots?.size ?: 0
    }

    override fun onBindViewHolder(holder: ParkingLotsViewHolder, position: Int) {
        val parking = parkingLots?.get(position)
        holder.bind(parking)

    }

}