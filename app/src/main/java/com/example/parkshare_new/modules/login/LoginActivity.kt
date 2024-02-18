package com.example.parkshare_new.modules.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.parkshare_new.HomepageActivity
import com.example.parkshare_new.R
import com.example.parkshare_new.dao.UserDatabase
import com.example.parkshare_new.models.LocalUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Timestamp

public class LoginActivity : AppCompatActivity(){
    private var emailField: EditText? = null
    private var passwordField: EditText? = null
    private var nextButton: Button? = null
    private var database: UserDatabase? = null

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        database = UserDatabase.getInstance(applicationContext)
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
                    lifecycleScope.launch(Dispatchers.IO)  {
                        val userDao = database!!.userDao()
                        userDao.insertUser(LocalUser(email = email, timestamp = System.currentTimeMillis()))
                    }
                    updateUI(user)
                } else {
                    // Login failed
                    Log.w("Login", "signUpWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    private fun updateUI(user : FirebaseUser?) {
        val intent = Intent(this, HomepageActivity::class.java)
        startActivity(intent)
    }
}