package com.example.parkshare_new.modules.addParking

import android.app.Activity
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Display.Mode
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.navigation.Navigation
import com.example.parkshare_new.R
import com.example.parkshare_new.databinding.FragmentAddParkingBinding
import com.example.parkshare_new.databinding.FragmentParkingBinding
import com.example.parkshare_new.models.Model
import com.example.parkshare_new.models.Parking
import java.net.URI

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

    private fun setupUI() {
        parkingImageField = binding.imParkingImageAddParking
        addressTextField = binding.ptAddressAddParking //view.findViewById(R.id.signinNameVal)
        cityTextField = binding.ptCityAddParking //view.findViewById(R.id.sininCityVal)
        uploadImageButton = binding.btnUploadImageAddParking
        saveButton = binding.btnSaveAddParking //view.findViewById(R.id.btnSaveSignin)
        cancelButton = binding.btnCancelAddParking //view.findViewById(R.id.btnCancelSignin)

        uploadImageButton?.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, REQUEST_CODE_IMAGE)
        }

        saveButton?.setOnClickListener {
            val address = addressTextField?.text ?: ""
            val city = cityTextField?.text ?: ""
            val avatar = selectedImageURI.toString()

            val parking = Parking(address.toString(), avatar, city.toString(), "", false, false, System.currentTimeMillis())
            Model.instance.addParking(parking) {
                Navigation.findNavController(it).popBackStack(R.id.parkingLotsFragment, false)
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