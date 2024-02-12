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
import com.example.parkshare_new.databinding.FragmentParkingBinding

class ParkingFragment : Fragment() {

    private var textView: TextView? = null
    private var title: String? = null

    private var _binding: FragmentParkingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentParkingBinding.inflate(inflater, container, false)
        val view = binding.root

        val nameTitle = arguments?.let {
            ParkingFragmentArgs.fromBundle(it).ADDRESS
        }
        textView = binding.tvParkingFragmentName //view.findViewById(R.id.tvParkingFragmentName)
        textView?.text = nameTitle ?: "Missing Name"

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}