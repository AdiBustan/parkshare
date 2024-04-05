package com.example.parkshare_new.modules.signUp;

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.parkshare_new.HomepageActivity
import com.example.parkshare_new.dao.UserDao
import com.example.parkshare_new.dao.UserDatabase
import com.example.parkshare_new.databinding.ActivitySignupBinding
import com.example.parkshare_new.models.LocalUser
import com.example.parkshare_new.models.Model
import com.example.parkshare_new.models.Profile
import com.example.parkshare_new.modules.addParking.AddParkingFragment
import com.example.parkshare_new.services.ImagesService
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


public class signUpActivity : AppCompatActivity() {

    var nameTextField: TextInputLayout? = null
    var faveCityTextField: TextInputLayout? = null
    var avatarImageView: ImageView? = null
    var emailField: TextInputLayout? = null
    var passwordField: TextInputLayout? = null
    var uploadImageButton: Button? = null
    var saveButton: Button? = null
    var cancelButton: Button? = null

    var email: String = ""
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
            username = nameTextField?.editText?.text.toString()
            faveCity = faveCityTextField?.editText?.text.toString()

            if (selectedImageURI != null) {
                avatar = ImagesService.uploadImageToStorage(selectedImageURI!!)
            }

            createAccount(passwordField?.editText?.text.toString())
            saveToFireStore()
        }

        cancelButton?.setOnClickListener {
            finish()
        }
    }

    private fun createAccount(password : String) {

        // Sign Up
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser

                    lifecycleScope.launch(Dispatchers.IO) {
                        Log.i("LocalStorage", "Start to save new user on local data")
                        userDao!!.insertUser(LocalUser(email, username, faveCity, avatar, System.currentTimeMillis()))
                        Log.i("LocalStorage", "save local user successfully, email: $email")
                    }

                } else {
                    // If sign up fails, display a message to the user.
                    Log.w("EmailPassword", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    private fun saveToFireStore() {
        val profile = Profile(email, username, faveCity, avatar, listOf())
        Model.instance.addUser(profile) {
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
