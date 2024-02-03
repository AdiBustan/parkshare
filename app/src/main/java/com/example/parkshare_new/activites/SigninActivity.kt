package com.example.parkshare_new.activites;

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.parkshare_new.R
import com.example.parkshare_new.modules.parkingLots.homepageActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        auth = Firebase.auth

        setupUI()
    }

    private fun setupUI() {
        nameTextField = findViewById(R.id.signinNameVal)
        CityTextField = findViewById(R.id.sininCityVal)
        emailField = findViewById(R.id.signinEmailVal)
        passwordField = findViewById(R.id.signinPasswordVal)
        saveButton = findViewById(R.id.btnSaveSignin)
        cancelButton = findViewById(R.id.btnCancelSignin)

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
        val intent = Intent(this, homepageActivity::class.java)
        startActivity(intent)
    }
}
