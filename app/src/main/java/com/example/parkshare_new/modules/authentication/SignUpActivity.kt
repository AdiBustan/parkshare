package com.example.parkshare_new.modules.authentication;

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.parkshare_new.modules.HomepageActivity
import com.example.parkshare_new.dao.UserDao
import com.example.parkshare_new.dao.UserDatabase
import com.example.parkshare_new.databinding.ActivitySignupBinding
import com.example.parkshare_new.models.LocalUser
import com.example.parkshare_new.models.Profile
import com.example.parkshare_new.models.UserModel
import com.example.parkshare_new.modules.parkingSpots.AddParkingFragment
import com.example.parkshare_new.services.DialogService
import com.example.parkshare_new.services.ImagesService
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class signUpActivity : AppCompatActivity() {

    var nameTextField: TextInputLayout? = null
    var faveCityTextField: TextInputLayout? = null
    var avatarImageView: ImageView? = null
    var emailField: TextInputLayout? = null
    var passwordField: TextInputLayout? = null
    var uploadImageButton: Button? = null
    var saveButton: Button? = null
    var cancelButton: Button? = null

    var email: String = ""
    var password: String = ""
    var username: String = ""
    var faveCity: String = ""
    var avatar: String = ""

    var database: UserDatabase? = null
    var userDao: UserDao? = null
    private var selectedImageURI: Uri? = null

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        database = UserDatabase.getInstance(applicationContext)
        userDao = database!!.userDao()

        setContentView(binding.root)
        auth = Firebase.auth
        setupUI()
    }

    private fun setupUI() {
        nameTextField = binding.ptNamesignUp
        faveCityTextField = binding.ptCitysignUp
        avatarImageView = binding.imAvatarSignup
        emailField = binding.signUpEmailVal
        passwordField = binding.signUpPasswordVal

        uploadImageButton = binding.btnUploadImageSignup
        saveButton = binding.btnSavesignUp
        cancelButton = binding.btnCancelsignUp

        uploadImageButton?.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, AddParkingFragment.REQUEST_CODE_IMAGE)
        }

        saveButton?.setOnClickListener {
            email = emailField?.editText?.text.toString()
            password = passwordField?.editText?.text.toString()
            username = nameTextField?.editText?.text.toString()
            faveCity = faveCityTextField?.editText?.text.toString()

            if (email.isNotEmpty() && username.isNotEmpty() && faveCity.isNotEmpty() && selectedImageURI != null && password.isNotEmpty()) {
                avatar = ImagesService.uploadImageToStorage(selectedImageURI!!)
                createAccount()
            } else {
                DialogService.showMissingDetailsDialog(this)
            }
        }

        cancelButton?.setOnClickListener {
            finish()
        }
    }

    private fun createAccount() {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        userDao!!.insertUser(LocalUser(email, username, faveCity, avatar, System.currentTimeMillis()))
                        saveToFireStore()
                    }

                } else {
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun saveToFireStore() {
        val profile = Profile(email, username, faveCity, avatar, listOf())
        UserModel.instance.addUser(profile) {
            val intent = Intent(this, HomepageActivity::class.java)
            startActivity(intent)
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
}
