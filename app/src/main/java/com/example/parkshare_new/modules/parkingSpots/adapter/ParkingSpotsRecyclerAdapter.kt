package com.example.parkshare_new.modules.parkingSpots.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.parkshare_new.R
import com.example.parkshare_new.HomepageActivity
import com.example.parkshare_new.models.Parking
import com.example.parkshare_new.modules.userProfile.UserProfileFragment

class ParkingSpotsRecyclerAdapter(var parkingSpots: List<Parking>?): RecyclerView.Adapter<ParkingSpotsViewHolder> () {
    var homepageListener: HomepageActivity.OnItemClickListener? = null
    var userProfileListener: UserProfileFragment.OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkingSpotsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.parking_list_row, parent, false)
        return ParkingSpotsViewHolder(itemView, homepageListener, userProfileListener, parkingSpots)
    }

    override fun getItemCount(): Int {
        return parkingSpots?.size ?: 0
    }

    override fun onBindViewHolder(holder: ParkingSpotsViewHolder, position: Int) {
        val parking = parkingSpots?.get(position)
        holder.bind(parking)

    }

}