package com.example.parkshare_new.modules.userProfile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import androidx.navigation.Navigation
import com.example.parkshare_new.databinding.FragmentEditParkingSpotBinding
import com.example.parkshare_new.models.ParkingSpotModel
import com.example.parkshare_new.models.Parking
import com.example.parkshare_new.modules.parkingSpots.AddParkingFragment
import com.example.parkshare_new.services.CitiesAPIService
import com.example.parkshare_new.services.DialogService
import com.example.parkshare_new.services.ImagesService
import com.google.android.material.textfield.TextInputLayout

class EditParkingSpotFragment : Fragment() {

    private var parkingImageView: ImageView? = null
    private var addressTextField: TextInputLayout? = null
    private var citySpinnerField: Spinner? = null
    private var updateImageButton: Button? = null
    private var chosenCity: String? = ""
    private var saveButton: Button? = null
    private var deleteButton: Button? = null

    var args: EditParkingSpotFragmentArgs? = null
    private var selectedImageURI: Uri? = null
    private lateinit var binding: FragmentEditParkingSpotBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        binding = FragmentEditParkingSpotBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = binding.root
        addressTextField = binding.ptAddressEditParking
        citySpinnerField = binding.spCityEditParking
        parkingImageView = binding.imParkingImageEditParking

        args = arguments?.let {
            EditParkingSpotFragmentArgs.fromBundle(it)
        }
        setupUI()


        return view
    }

    private fun setupUI() {

        addressTextField!!.editText?.setText(args!!.ADRESS)
        CitiesAPIService.fetchCitiesInIsraelFromAPI(requireContext(), citySpinnerField!!, args!!.CITY)
        ImagesService.loadingImageFromStorage(parkingImageView!!, args!!.AVATAR)

        updateImageButton = binding.btnUpdateImageEditParking
        saveButton = binding.btnSaveEditParking
        deleteButton = binding.btnDeleteEditParking

        updateImageButton?.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, AddParkingFragment.REQUEST_CODE_IMAGE)
        }

        citySpinnerField!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                chosenCity = parent?.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                chosenCity = "none"
            }
        }

        saveButton?.setOnClickListener {
            val address = addressTextField?.editText?.text.toString()
            var avatar = args!!.AVATAR
            if (selectedImageURI != null) {
                avatar = ImagesService.uploadImageToStorage(selectedImageURI!!)
            }

            if (address.isNotEmpty() && !chosenCity.equals("none") && avatar != "") {
                saveToFireStore(address, avatar)
            } else {
                DialogService.showMissingDetailsDialog(context)
            }

        }

        deleteButton?.setOnClickListener {
            ParkingSpotModel.instance.deleteParking(args!!.TIMESTAMP) {
                Navigation.findNavController(it).popBackStack()
            }
        }
    }

    private fun saveToFireStore(address: String, avatar: String) {
        val parkingSpot = Parking(args!!.TIMESTAMP, address, avatar, chosenCity!!, args!!.OWNER, false, false)
        ParkingSpotModel.instance.updateParkingSpot(parkingSpot) {
            view?.let { Navigation.findNavController(it).popBackStack() }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AddParkingFragment.REQUEST_CODE_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageURI = data.data
            parkingImageView?.setImageURI(selectedImageURI)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }
}