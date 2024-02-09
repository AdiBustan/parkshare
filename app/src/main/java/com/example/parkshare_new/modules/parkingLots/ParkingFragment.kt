package com.example.parkshare_new.modules.parkingLots

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.Navigation
import com.example.parkshare_new.R

class ParkingFragment : Fragment() {

    private var textView: TextView? = null
    private var title: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_parking, container, false)

        val nameTitle = arguments?.let {
            ParkingFragmentArgs.fromBundle(it).ADDRESS
        }
        textView = view.findViewById(R.id.tvParkingFragmentName)
        textView?.text = nameTitle ?: "Missing Name"

        return view
    }
}