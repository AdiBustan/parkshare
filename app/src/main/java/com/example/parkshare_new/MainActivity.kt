package com.example.parkshare_new

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val signinButton : Button = findViewById(R.id.SigninButton)
        val loginButton : Button = findViewById(R.id.alreadySignButton)

        signinButton.setOnClickListener(::onSigninButtonClicked)
        loginButton.setOnClickListener(::onLoginPageButtonClicked)
    }

    fun onSigninButtonClicked(view: View) {
        val intent = Intent(this, SigninActivity::class.java)
        startActivity(intent)
    }

    fun onLoginPageButtonClicked(view: View) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}