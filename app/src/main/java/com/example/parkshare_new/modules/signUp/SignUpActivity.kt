package com.example.parkshare_new.modules.signUp;

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.parkshare_new.HomepageActivity
import com.example.parkshare_new.dao.UserDao
import com.example.parkshare_new.dao.UserDatabase
import com.example.parkshare_new.databinding.ActivitySignupBinding
import com.example.parkshare_new.models.LocalUser
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


public class signUpActivity : AppCompatActivity() {

    var nameTextField: TextInputLayout? = null
    var CityTextField: TextInputLayout? = null
    var emailField: TextInputLayout? = null
    var passwordField: TextInputLayout? = null
    var saveButton: Button? = null
    var cancelButton: Button? = null
    var database: UserDatabase? = null
    var userDao: UserDao? = null

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
        CityTextField = binding.ptCitysignUp
        emailField = binding.signUpEmailVal
        passwordField = binding.signUpPasswordVal
        saveButton = binding.btnSavesignUp
        cancelButton = binding.btnCancelsignUp //findViewById(R.id.btnCancelsignUp)

        saveButton?.setOnClickListener {
            createAccount(emailField?.editText?.text.toString(), passwordField?.editText?.text.toString())
        }

        cancelButton?.setOnClickListener {
            finish()
        }
    }

    private fun createAccount(email : String, password : String) {

        // Sign Up
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser

                    lifecycleScope.launch(Dispatchers.IO) {
                        Log.i("LocalStorage", "Start to save new user on local data")
                        userDao!!.insertUser(LocalUser(email = email, timestamp = System.currentTimeMillis()))
                        Log.i("LocalStorage", "save local user successfully, email: $email")
                    }

                    // Update UI or do something with the user information
                    updateUI(user)

                } else {
                    // If sign up fails, display a message to the user.
                    Log.w("EmailPassword", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null)

//                    val intent = Intent(this, MainActivity::class.java)
//                    startActivity(intent)
                }
            }
    }

    private fun updateUI(user :FirebaseUser?) {
        val intent = Intent(this, HomepageActivity::class.java)
        startActivity(intent)
    }
}
