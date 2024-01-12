package com.example.parkshare_new;

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

public class SigninActivity : AppCompatActivity() {

    var firstNameTextField: EditText? = null
    var lastNameTextField: EditText? = null
    var messageTextView: EditText? = null
    var saveButton: Button? = null
    var cancelButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        setupUI()
    }

    private fun setupUI() {
        firstNameTextField = findViewById(R.id.signinFirstNameVal)
        lastNameTextField = findViewById(R.id.signinLastNameVal)
        saveButton = findViewById(R.id.btnSaveSignin)
        cancelButton = findViewById(R.id.btnCancelSignin)

        saveButton?.setOnClickListener {
            val name = lastNameTextField?.text.toString()

        }

        cancelButton?.setOnClickListener {
            finish()

        }
    }
}
