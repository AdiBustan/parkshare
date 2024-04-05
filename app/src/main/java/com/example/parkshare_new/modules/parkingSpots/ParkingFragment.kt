package com.example.parkshare_new.modules.parkingSpots

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.parkshare_new.databinding.FragmentParkingBinding
import com.example.parkshare_new.services.ImagesService

class ParkingFragment : Fragment() {

    private var addressTextView: TextView? = null
    private var cityTextView: TextView? = null
    private lateinit var avatarImageView: ImageView

    private lateinit var address: String
    private lateinit var city: String
    private lateinit var avatar: String

    private var _binding: FragmentParkingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentParkingBinding.inflate(inflater, container, false)
        val view = binding.root
        val arguments = arguments?.let {
            ParkingFragmentArgs.fromBundle(it)
        }
        address = arguments!!.ADDRESS
        city = arguments.CITY
        avatar = arguments.AVATAR

        addressTextView = binding.tvAddressParkingFragment
        cityTextView = binding.tvCityParkingFragment
        avatarImageView = binding.imAvatarParkingFragment

        addressTextView?.text = address
        cityTextView?.text = city
        ImagesService.loadingImageFromStorage(requireContext(), avatarImageView, avatar)

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}