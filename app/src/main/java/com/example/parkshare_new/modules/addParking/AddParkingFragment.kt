package com.example.parkshare_new.modules.addParking

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Display.Mode
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.parkshare_new.R
import com.example.parkshare_new.dao.UserDatabase
import com.example.parkshare_new.databinding.FragmentAddParkingBinding
import com.example.parkshare_new.databinding.FragmentParkingBinding
import com.example.parkshare_new.models.LocalUser
import com.example.parkshare_new.models.Model
import com.example.parkshare_new.models.Parking
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import java.net.URI
import kotlin.math.log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class AddParkingFragment : Fragment() {

    private var parkingImageField: ImageView? = null
    private var addressTextField: TextInputLayout? = null
    private var cityTextField: TextInputLayout? = null
    private var uploadImageButton: Button? = null
    private var saveButton: Button? = null
    private var cancelButton: Button? = null

    private var fileName: String = ""
    private var selectedImageURI: Uri? = null
    private var downloadUrl: String? = null
    private var _binding: FragmentAddParkingBinding? = null

    private var database: UserDatabase? = null
    private var currUser: LocalUser? = null

    private val binding get() = _binding!!

    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference

    companion object {
        const val REQUEST_CODE_IMAGE = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
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
        addressTextField = binding.ptAddressAddParking //view.findViewById(R.id.signUpNameVal)
        cityTextField = binding.ptCityAddParking //view.findViewById(R.id.sininCityVal)
        uploadImageButton = binding.btnUploadImageAddParking
        saveButton = binding.btnSaveAddParking //view.findViewById(R.id.btnSavesignUp)
        cancelButton = binding.btnCancelAddParking //view.findViewById(R.id.btnCancelsignUp)

        uploadImageButton?.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, REQUEST_CODE_IMAGE)
        }

        saveButton?.setOnClickListener {
            val address = addressTextField?.editText?.text
            val city = cityTextField?.editText?.text
            if (selectedImageURI != null) {
                this.uploadToStorage()
            }
            val avatar = fileName

            if (address?.isNotEmpty() == true && city?.isNotEmpty() == true) {
                val parking = Parking(address.toString(), avatar, city.toString(), currUser!!.email, false, false, System.currentTimeMillis())
                Model.instance.addParking(parking) {
                    Navigation.findNavController(it).popBackStack(R.id.parkingLotsFragment, false)
                }
            } else {
                val errorMessage = if (address?.isNotEmpty() == true) { "city name" }
                                else if (city?.isNotEmpty() ==  true) { "address" }
                                else { "address and city name" }
                showErrorParkingSpotDialog(errorMessage)
            }
        }

        cancelButton?.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
    }

    private fun uploadToStorage() {
        val formatter = SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.FRANCE)
        val now = Date()
        fileName = formatter.format(now)
        val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")

        storageReference.putFile(selectedImageURI!!).
                addOnSuccessListener {
                    //binding.imParkingImageAddParking.setImageURI(null)
                    //val storageRef = FirebaseStorage.getInstance().reference.child("images/$fileName")
                }.addOnFailureListener {
        }
    }

    private fun showErrorParkingSpotDialog(textMessage: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error saving your parking spot")
            .setMessage("Please enter $textMessage")

        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageURI = data.data
            parkingImageField?.setImageURI(selectedImageURI)
        }
    }
}