package com.example.parkshare_new.activites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.parkshare_new.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

public class LoginActivity : AppCompatActivity(){
    private var emailField: EditText? = null
    private var passwordField: EditText? = null
    private var nextButton: Button? = null

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth

        setupUI()
    }

    private fun setupUI() {
        emailField = findViewById(R.id.login_email)
        passwordField = findViewById(R.id.login_password)
        nextButton = findViewById(R.id.next_button)

        nextButton?.setOnClickListener {
            // TODO - if login dont work just comment this row
            tryToLogin(emailField?.text.toString(), passwordField?.text.toString())
            updateUI(null)
        }
    }

    private fun tryToLogin(email : String, password : String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Login successful
                    val user = auth.currentUser

                    updateUI(user)
                } else {
                    // Login failed
                    Log.w("Login", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    private fun updateUI(user : FirebaseUser?) {
        val intent = Intent(this, homepageActivity::class.java)
        startActivity(intent)
    }
}