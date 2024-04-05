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
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.parkshare_new.HomepageActivity
import com.example.parkshare_new.R
import com.example.parkshare_new.dao.UserDao
import com.example.parkshare_new.dao.UserDatabase
import com.example.parkshare_new.databinding.ActivitySignupBinding
import com.example.parkshare_new.databinding.FragmentEditUserProfileBinding
import com.example.parkshare_new.models.LocalUser
import com.example.parkshare_new.models.Model
import com.example.parkshare_new.models.Profile
import com.example.parkshare_new.modules.addParking.AddParkingFragment
import com.example.parkshare_new.modules.parkingSpots.ParkingFragmentArgs
import com.example.parkshare_new.modules.parkingSpots.ParkingFragmentArgs.Companion.fromBundle
//import com.example.parkshare_new.modules.userProfile.EditUserProfileFragmentArgs.Companion.fromBundle
import com.example.parkshare_new.services.ImagesService
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class EditUserProfileFragment : Fragment() {

    var nameTextField: TextInputLayout? = null
    var faveCityTextField: TextInputLayout? = null
    var avatarImageView: ImageView? = null
    var updateImageButton: Button? = null
    var saveButton: Button? = null
    var cancelButton: Button? = null
    //var args: EditUserProfileFragmentArgs? = null
    private var selectedImageURI: Uri? = null

    private var database: UserDatabase? = null
    private var currUser: LocalUser? = null
    private lateinit var binding: FragmentEditUserProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        binding = FragmentEditUserProfileBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = binding.root
        nameTextField = binding.ptNameEditProfile
        faveCityTextField = binding.ptCityEditProfile
        avatarImageView = binding.imAvatarEditProfile

//        args = arguments?.let {
//            EditUserProfileFragmentArgs.fromBundle(it)
//        }

        database = UserDatabase.getInstance(requireContext().applicationContext)
        lifecycleScope.launch(Dispatchers.IO)  {
            val userDao = database!!.userDao()
            currUser = userDao.getUser()

            withContext(Dispatchers.Main) {
                // Call setupUI() after currUser is retrieved and logged
                setupUI()
            }
        }

        return view
    }

    private fun setupUI() {
//        nameTextField!!.hint = args!!.USERNAME
//        faveCityTextField!!.hint = args!!.FAVECITY
//        ImagesService.loadingImageFromStorage(requireContext(), avatarImageView!!, args!!.AVATAR)

        updateImageButton = binding.btnUploadImageEditProfile
        saveButton = binding.btnSaveEditProfile
        cancelButton = binding.btnCancelEditProfile

        updateImageButton?.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, AddParkingFragment.REQUEST_CODE_IMAGE)
        }

        saveButton?.setOnClickListener {
            val name = nameTextField?.editText?.text.toString()
            val city = faveCityTextField?.editText?.text.toString()

            var avatar = ""
            if (selectedImageURI != null) {
                avatar = ImagesService.uploadImageToStorage(selectedImageURI!!)
            }
            //saveToFireStore(args!!.EMAIL, name, city, avatar)

        }

        cancelButton?.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

    }

    private fun saveToFireStore(email: String, username: String, faveCity: String, avatar: String) {
        val profile = Profile(email, username, faveCity, avatar, listOf())
        Model.instance.updateUserDetails(profile) {
            view?.let { Navigation.findNavController(it).popBackStack() }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AddParkingFragment.REQUEST_CODE_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageURI = data.data
            avatarImageView?.setImageURI(selectedImageURI)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }
}