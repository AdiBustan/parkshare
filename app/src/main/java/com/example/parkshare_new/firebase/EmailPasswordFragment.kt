package com.example.parkshare_new.firebase

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.parkshare_new.activites.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class EmailPasswordFragment : Fragment() {

//    private lateinit var auth: FirebaseAuth
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        // Initialize Firebase Auth
//        auth = Firebase.auth
//    }
//
//    private fun createAccount(email : String, password : String) {
//
//        // Sign Up
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//
//                    // Sign in success, update UI with the signed-in user's information
//                    Log.d(ContentValues.TAG, "createUserWithEmail:success")
//                    val user = auth.currentUser
//                    // Update UI or do something with the user information
//                    updateUI(user)
//
//                } else {
//                    // If sign up fails, display a message to the user.
//                    Log.w("EmailPassword", "createUserWithEmail:failure", task.exception)
//                    Toast.makeText(
//                        baseContext,
//                        "Authentication failed.",
//                        Toast.LENGTH_SHORT,
//                    ).show()
//                    updateUI(null)
//
//                    val intent = Intent(this, MainActivity::class.java)
//                    startActivity(intent)
//                }
//            }
//    }
}