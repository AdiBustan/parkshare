package com.example.parkshare_new.modules.parkingLots.adapter

import android.app.AlertDialog
import android.graphics.BitmapFactory
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.parkshare_new.R
import com.example.parkshare_new.HomepageActivity
import com.example.parkshare_new.models.Model
import com.example.parkshare_new.models.Parking
import com.example.parkshare_new.modules.parkingLots.RemoveParkingFragment
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class ParkingLotsViewHolder(
    itemView: View,
    val listener: HomepageActivity.OnItemClickListener?,
    var parkingLots: List<Parking>?): RecyclerView.ViewHolder(itemView) {

    var parkingAddressTextView : TextView? = null
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
            parking = parkingLots?.get(adapterPosition)
            parking?.isChecked = parkingCheckBox?.isChecked ?: false

            if (parking?.isChecked == true) {
                showUnavailableConfirmationDialog()
            }
        }

        itemView.setOnClickListener{
            Log.i("TAG", "ParkingLotsViewHolder: position clicked: $adapterPosition")
            listener?.onItemClick(adapterPosition)
            listener?.onParkingClicked(parking)
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

        loadingImageFromStorage(parking?.avatar)

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

    private fun loadingImageFromStorage(imageName: String?) {
        if (imageName?.isNotEmpty() == true) {
            val storageReference = FirebaseStorage.getInstance().reference.child("/images/$imageName")

            val localFile = File.createTempFile("tempImage", "jpg")
            storageReference.getFile(localFile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                parkingAvatarImageView.setImageBitmap(bitmap)

            }.addOnFailureListener {
                Log.d("Tag", "Faild To Load Image ")
            }
        } else {
            Glide.with(itemView.context)
                .load(R.drawable.parking_icon)
                .placeholder(R.drawable.loading)
                .into(parkingAvatarImageView)
        }
    }
}