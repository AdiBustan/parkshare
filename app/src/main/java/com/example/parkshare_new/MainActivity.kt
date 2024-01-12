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

        signinButton.setOnClickListener(::onSigninBottonClicked) }

    fun onSigninBottonClicked(view: View) {
        val intent = Intent(this, SigninActivity::class.java)
        startActivity(intent)
    }
}