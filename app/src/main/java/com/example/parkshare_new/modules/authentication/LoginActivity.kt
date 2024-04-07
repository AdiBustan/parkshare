package com.example.parkshare_new.modules.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.parkshare_new.modules.HomepageActivity
import com.example.parkshare_new.R
import com.example.parkshare_new.dao.UserDao
import com.example.parkshare_new.dao.UserDatabase
import com.example.parkshare_new.models.LocalUser
import com.example.parkshare_new.services.DialogService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private var emailField: EditText? = null
    private var passwordField: EditText? = null
    private var nextButton: Button? = null
    private var database: UserDatabase? = null
    private var userDao: UserDao? = null

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        database = UserDatabase.getInstance(applicationContext)
        userDao = database!!.userDao()
        auth = Firebase.auth

        setupUI()
    }

    private fun setupUI() {
        emailField = findViewById(R.id.login_email)
        passwordField = findViewById(R.id.login_password)
        nextButton = findViewById(R.id.next_button)

        nextButton?.setOnClickListener {
            val email = emailField?.text.toString()
            val password = passwordField?.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                tryToLogin(email, password)
            } else {
                DialogService.showMissingDetailsDialog(this)
            }

        }
    }

    private fun tryToLogin(email : String, password : String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    lifecycleScope.launch(Dispatchers.IO)  {
                        userDao!!.insertUser(LocalUser(email, "", "", "",System.currentTimeMillis()))
                        updateUI()
                    }

                } else {
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun updateUI() {
        val intent = Intent(this, HomepageActivity::class.java)
        startActivity(intent)
    }
}