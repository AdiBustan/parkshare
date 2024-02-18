package com.example.parkshare_new.modules.addParking

import android.app.Activity
import android.content.Intent
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
import java.net.URI
import kotlin.math.log

class AddParkingFragment : Fragment() {

    private var parkingImageField: ImageView? = null
    private var addressTextField: EditText? = null
    private var cityTextField: EditText? = null
    private var uploadImageButton: Button? = null
    private var saveButton: Button? = null
    private var cancelButton: Button? = null


    private var selectedImageURI: Uri? = null
    private var _binding: FragmentAddParkingBinding? = null

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
            val address = addressTextField?.text ?: ""
            val city = cityTextField?.text ?: ""
            val avatar = selectedImageURI.toString()

            Log.i("LocalStorage", "AddParking: user email from local store is: " + currUser!!.email)
            val parking = Parking(address.toString(), avatar, city.toString(), currUser!!.email, false, false, System.currentTimeMillis())
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