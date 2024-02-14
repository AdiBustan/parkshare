package com.example.parkshare_new.modules.parkingLots

import android.app.AlertDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class RemoveParkingFragment : Fragment() {

    fun showPopupDialog(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Popup Title")
            .setMessage("This is a simple popup message.")

        // Positive button (OK)
        builder.setPositiveButton("OK") { dialog, _ ->
            // Handle button click
            dialog.dismiss()
        }

        // Negative button (Cancel)
        builder.setNegativeButton("Cancel") { dialog, _ ->
            // Handle button click
            dialog.dismiss()
        }

        // Get the FragmentManager from the context
        val fragmentManager: FragmentManager = (context as AppCompatActivity).supportFragmentManager

        // Create and show the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }
}