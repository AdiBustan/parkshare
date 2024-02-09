package com.example.parkshare_new

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.parkshare_new.modules.signin.SigninActivity
import com.example.parkshare_new.modules.login.LoginActivity
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    //abstract var passwordFragment :EmailPasswordFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_main)

        val signingButton : Button = findViewById(R.id.SigninButton)
        val loginButton : Button = findViewById(R.id.alreadySignButton)

        signingButton.setOnClickListener(::onSigninBottonClicked)
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
        //    TODO: remove comment - this func initiate the app with already signin user
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


    private fun onSigninBottonClicked(view: View) {
        val intent = Intent(this, SigninActivity::class.java)
        startActivity(intent)
    }

    fun onLoginPageButtonClicked(view: View) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}