package com.example.parkshare_new.modules.parkingSpots.adapter

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parkshare_new.R
import com.example.parkshare_new.modules.HomepageActivity
import com.example.parkshare_new.models.Parking
import com.example.parkshare_new.services.DialogService
import com.example.parkshare_new.services.ImagesService

class ParkingSpotsViewHolder(
    itemView: View,
    val listener: HomepageActivity.OnItemClickListener?,
    var parkingSpots: List<Parking>?): RecyclerView.ViewHolder(itemView) {

    var parkingAddressTextView: TextView? = null
    var parkingCityTextView: TextView? = null
    var parkingAvatarImageView: ImageView
    var parkingCheckBox: CheckBox? = null
    var parking: Parking? = null

    init {
        parkingAddressTextView = itemView.findViewById(R.id.tvParkingAdress)
        parkingCityTextView = itemView.findViewById(R.id.tvParkingCity)
        parkingAvatarImageView = itemView.findViewById(R.id.imParkingImage)
        parkingCheckBox = itemView.findViewById(R.id.cbAvalibleParking)

        parkingCheckBox?.setOnClickListener {
            parking = parkingSpots?.get(adapterPosition)
            parking?.isChecked = parkingCheckBox?.isChecked ?: false

            if (parking?.isChecked == true) {
                DialogService.showUnavailableConfirmationDialog(parkingCheckBox!!, parking, itemView.context)
            }
        }

        itemView.setOnClickListener {
            listener?.onItemClick(adapterPosition)
        }
    }

    fun bind(parking: Parking?) {
        this.parking = parking
        parkingAddressTextView?.text = parking?.address
        parkingCityTextView?.text = parking?.city

        ImagesService.loadingImageFromStorage(parkingAvatarImageView, parking?.avatar)

        parkingCheckBox?.apply {
            if (parking?.isUnavailable == true) {
                text = "unavailable"
                isClickable = false
                isFocusable = false
                buttonDrawable = null
            } else {
                isChecked = false
            }
        }
    }
}