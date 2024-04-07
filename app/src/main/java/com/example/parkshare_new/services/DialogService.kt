package com.example.parkshare_new.services

import android.app.AlertDialog
import android.content.Context
import android.widget.CheckBox
import com.example.parkshare_new.models.Parking
import com.example.parkshare_new.models.ParkingSpotModel

class DialogService {


    companion object {

        fun showMissingDetailsDialog(context: Context?) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Missing Details")
                .setMessage("You must fill all requested fields")

            builder.setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }

            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        }

        fun showUnavailableConfirmationDialog(checkBox: CheckBox, parking: Parking?, context: Context?) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Not Available Parking")
                .setMessage("Are you sure this parking isn't available?")

            builder.setPositiveButton("Yes") { dialog, _ ->
                checkBox.text = "unavailable"
                checkBox.isClickable = false
                checkBox.isFocusable = false
                checkBox.buttonDrawable = null
                parking?.let {
                    it.isUnavailable = true
                    ParkingSpotModel.instance.updateParkingSpot(it) {
                        dialog.dismiss()
                    }
                }
            }
            builder.setNegativeButton("No") { dialog, _ ->
                checkBox.apply {
                    isChecked = false
                }
                dialog.dismiss()
            }

            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        }
    }
}