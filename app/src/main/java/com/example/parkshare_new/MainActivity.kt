package com.example.parkshare_new

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.parkshare_new.databinding.ActivityMainBinding
import com.example.parkshare_new.modules.login.LoginActivity
import com.example.parkshare_new.modules.signUp.signUpActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        FirebaseApp.initializeApp(this)
        setContentView(binding.root)

        val signUpButton : Button = binding.signUpButton
        val loginButton : Button = binding.alreadySignButton

        signUpButton.setOnClickListener(::onSignUpButtonClicked)
        loginButton.setOnClickListener(::onLoginPageButtonClicked)
    }

    //TODO: remove comment in case EmailPasswordFragment.kt is working
//    public override fun onStart() {
//        super.onStart()
//        passwordFragment.outoConnect()
//    }

    public override fun onStart() {
        super.onStart()
        auth = Firebase.auth
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        //    TODO: remove comment - this func initiate the app with already signUp user
//        if (currentUser != null) {
//            reload()
//        }
    }

    private fun reload() {
        auth.currentUser!!.reload().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val intent = Intent(this, HomepageActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "Reload successful!", Toast.LENGTH_SHORT).show()
            } else {
                Log.e(ContentValues.TAG, "reload", task.exception)
                Toast.makeText(this, "Failed to reload user.", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun onSignUpButtonClicked(view: View) {
        val intent = Intent(this, signUpActivity::class.java)
        startActivity(intent)
    }

    fun onLoginPageButtonClicked(view: View) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}