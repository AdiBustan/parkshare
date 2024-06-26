package com.example.parkshare_new.modules.parkingSpots

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.parkshare_new.R
import com.example.parkshare_new.dao.UserDatabase
import com.example.parkshare_new.databinding.FragmentAddParkingBinding
import com.example.parkshare_new.models.LocalUser
import com.example.parkshare_new.models.ParkingSpotModel
import com.example.parkshare_new.models.Parking
import com.example.parkshare_new.services.CitiesAPIService
import com.example.parkshare_new.services.DialogService
import com.example.parkshare_new.services.ImagesService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.google.android.material.textfield.TextInputLayout

class AddParkingFragment : Fragment() {

    private var parkingImageField: ImageView? = null
    private var addressTextField: TextInputLayout? = null
    private var citySpinnerField: Spinner? = null
    private var uploadImageButton: Button? = null
    private var saveButton: Button? = null
    private var cancelButton: Button? = null

    private var selectedImageURI: Uri? = null
    private var _binding: FragmentAddParkingBinding? = null
    private var chosenCity: String? = ""
    private var database: UserDatabase? = null
    private var currUser: LocalUser? = null

    private val binding get() = _binding!!

    companion object {
        const val REQUEST_CODE_IMAGE = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddParkingBinding.inflate(inflater, container, false)
        val view = binding.root

        database = UserDatabase.getInstance(requireContext().applicationContext)
        lifecycleScope.launch(Dispatchers.IO)  {
            val userDao = database!!.userDao()
            currUser = userDao.getUser()
        }

        setupUI()

        return view
    }

    private fun setupUI() {
        parkingImageField = binding.imParkingImageAddParking
        addressTextField = binding.ptAddressAddParking
        citySpinnerField = binding.spCityAddParking
        uploadImageButton = binding.btnUploadImageAddParking
        saveButton = binding.btnSaveAddParking
        cancelButton = binding.btnCancelAddParking

        CitiesAPIService.fetchCitiesInIsraelFromAPI(requireContext(), citySpinnerField!!, "City")

        uploadImageButton?.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, REQUEST_CODE_IMAGE)
        }

        citySpinnerField!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                chosenCity = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        saveButton?.setOnClickListener {
            val address = addressTextField?.editText?.text

            if (address?.isNotEmpty() == true && chosenCity != "" && selectedImageURI != null) {
                val avatar = ImagesService.uploadImageToStorage(selectedImageURI!!)
                val parking = Parking( System.currentTimeMillis(), address.toString(), avatar, chosenCity!!, currUser!!.email, false, false)
                ParkingSpotModel.instance.addParking(parking) {
                    Navigation.findNavController(it).popBackStack(R.id.parkingSpotsFragment, false)
                }
            } else {
                DialogService.showMissingDetailsDialog(context)
            }
        }

        cancelButton?.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageURI = data.data
            parkingImageField?.setImageURI(selectedImageURI)
        }
    }
}