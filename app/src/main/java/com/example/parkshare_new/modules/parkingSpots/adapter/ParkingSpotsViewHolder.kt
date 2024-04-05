package com.example.parkshare_new.modules.parkingSpots.adapter

import android.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parkshare_new.R
import com.example.parkshare_new.HomepageActivity
import com.example.parkshare_new.models.Model
import com.example.parkshare_new.models.Parking
import com.example.parkshare_new.modules.userProfile.UserProfileFragment
import com.example.parkshare_new.services.ImagesService

class ParkingSpotsViewHolder(
    itemView: View,
    val homepageListener: HomepageActivity.OnItemClickListener?,
    val userProfileListener: UserProfileFragment.OnItemClickListener?,
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
                showUnavailableConfirmationDialog()
            }
        }

        itemView.setOnClickListener {
            homepageListener?.onItemClick(adapterPosition)
            homepageListener?.onParkingClicked(parking)
            //TODO - add onclick userprofile listener
        }
    }

    private fun showUnavailableConfirmationDialog() {
        val builder = AlertDialog.Builder(itemView.context)
        builder.setTitle("Not Available Parking")
            .setMessage("Are you sure this parking isn't available?")

        builder.setPositiveButton("Yes") { dialog, _ ->
            parkingCheckBox?.text = "unavailable"
            parkingCheckBox?.isClickable = false
            parkingCheckBox?.isFocusable = false
            parkingCheckBox?.buttonDrawable = null
            parking?.let {
                it.isUnavailable = true
                Model.instance.updateToUnavailable(it) {
                    dialog.dismiss()
                }
            }
        }
        builder.setNegativeButton("No") { dialog, _ ->
            parkingCheckBox?.apply {
                isChecked = false
            }
            dialog.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    fun bind(parking: Parking?) {
        this.parking = parking
        parkingAddressTextView?.text = parking?.address
        parkingCityTextView?.text = parking?.city

        ImagesService.loadingImageFromStorage(itemView.context, parkingAvatarImageView, parking?.avatar)

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