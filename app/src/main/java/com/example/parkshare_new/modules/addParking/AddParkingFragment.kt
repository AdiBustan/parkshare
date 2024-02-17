package com.example.parkshare_new.modules.addParking

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
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
import androidx.navigation.Navigation
import com.example.parkshare_new.R
import com.example.parkshare_new.databinding.FragmentAddParkingBinding
import com.example.parkshare_new.databinding.FragmentParkingBinding
import com.example.parkshare_new.models.Model
import com.example.parkshare_new.models.Parking
import java.net.URI
import java.util.jar.Manifest

@Suppress("DEPRECATION")
class AddParkingFragment : Fragment() {

    private var parkingImageField: ImageView? = null
    private var addressTextField: EditText? = null
    private var cityTextField: EditText? = null
    private var uploadImageButton: Button? = null
    private var saveButton: Button? = null
    private var cancelButton: Button? = null


    private var selectedImageURI: Uri? = null
    private var _binding: FragmentAddParkingBinding? = null
    private val binding get() = _binding!!
    private val REQUEST_IMAGE_CAPTURE = 1
    private val CAMERA_PERMISSION_REQUEST_CODE = 1

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
        setupUI()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uploadImageButton = view.findViewById(R.id.imParkingImageAddParking)
        uploadImageButton?.setOnClickListener {
            dispatchTakePictureIntent()
        }

    }

    private fun setupUI() {
        parkingImageField = binding.imParkingImageAddParking
        addressTextField = binding.ptAddressAddParking //view.findViewById(R.id.signinNameVal)
        cityTextField = binding.ptCityAddParking //view.findViewById(R.id.sininCityVal)
        uploadImageButton = binding.btnUploadImageAddParking
        saveButton = binding.btnSaveAddParking //view.findViewById(R.id.btnSaveSignin)
        cancelButton = binding.btnCancelAddParking //view.findViewById(R.id.btnCancelSignin)

//        uploadImageButton?.setOnClickListener {
////            OPEN GALLERY CODE:
////            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
////            startActivityForResult(galleryIntent, REQUEST_CODE_IMAGE)
////        }

        saveButton?.setOnClickListener {
            val address = addressTextField?.text ?: ""
            val city = cityTextField?.text ?: ""
            val avatar = selectedImageURI.toString()

            val parking = Parking(address.toString(), avatar, city.toString(), false, false, System.currentTimeMillis())
            Model.instance.addParking(parking) {
                Navigation.findNavController(it).popBackStack(R.id.parkingLotsFragment, false)
            }
        }

        cancelButton?.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } else {
            Log.d("YourFragment", "Button Clicked") // Add log statement to check if the button is clicked

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as? Bitmap
            parkingImageField?.setImageBitmap(imageBitmap)
        }

//        OPEN GALLERY CODE:
//        if (requestCode == REQUEST_CODE_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
//            selectedImageURI = data.data
//            parkingImageField?.setImageURI(selectedImageURI)
//        }


    }
}