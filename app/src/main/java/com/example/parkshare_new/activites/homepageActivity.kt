package com.example.parkshare_new.activites

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.parkshare_new.R
import com.google.firebase.FirebaseApp

class homepageActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        setContentView(R.layout.activity_homepage)

    }


}