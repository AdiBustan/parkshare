package com.example.parkshare_new.modules.signin;

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.parkshare_new.R
import com.example.parkshare_new.HomepageActivity
import com.example.parkshare_new.databinding.ActivityMainBinding
import com.example.parkshare_new.databinding.ActivitySigninBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


public class SigninActivity : AppCompatActivity() {

    var nameTextField: EditText? = null
    var CityTextField: EditText? = null
    var emailField: EditText? = null
    var passwordField: EditText? = null
    var saveButton: Button? = null
    var cancelButton: Button? = null

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)

        setContentView(binding.root)
        auth = Firebase.auth

        setupUI()
    }

    private fun setupUI() {
        nameTextField = binding.ptNameSignin
        CityTextField = binding.ptCitySignin
        emailField = binding.signinEmailVal
        passwordField = binding.signinPasswordVal
        saveButton = binding.btnSaveSignin
        cancelButton = binding.btnCancelSignin

        saveButton?.setOnClickListener {
            createAccount(emailField?.text.toString(), passwordField?.text.toString())
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
                    //updateUI(null)

                    //val intent = Intent(this, MainActivity::class.java)
                    //startActivity(intent)
                }
            }
    }

    private fun updateUI(user :FirebaseUser?) {
        val intent = Intent(this, HomepageActivity::class.java)
        startActivity(intent)
    }
}
