package com.example.parkshare_new.activites

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.parkshare_new.R

public class LoginActivity : AppCompatActivity(){
    var emailField: EditText? = null
    var passwordField: EditText? = null
    var nextButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setupUI()
    }

    private fun setupUI() {
        emailField = findViewById(R.id.login_email)
        passwordField = findViewById(R.id.login_password)
        nextButton = findViewById(R.id.next_button)

//        nextButton?.setOnClickListener {
//            //todo - get into home page
//
//        }

    }
}