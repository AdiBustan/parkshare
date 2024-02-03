package com.example.parkshare_new.modules.parkingLots.adapter

import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parkshare_new.R
import com.example.parkshare_new.models.Parking
import com.example.parkshare_new.modules.parkingLots.homepageActivity

class ParkingLotsViewHolder(val itemView: View,
                            val listener: homepageActivity.OnItemClickListener?,
                            var parkingLots: MutableList<Parking>?): RecyclerView.ViewHolder(itemView) {

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