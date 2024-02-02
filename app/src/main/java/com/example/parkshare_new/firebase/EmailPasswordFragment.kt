package com.example.parkshare_new.firebase

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.parkshare_new.activites.MainActivity
import com.example.parkshare_new.activites.homepageActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class EmailPasswordFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize Firebase Auth
        auth = Firebase.auth
    }

    private fun createAccount(email : String, password : String) {

        // Sign Up
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {

                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    // Update UI or do something with the user information
                    updateUI(user)

                } else {
                    // If sign up fails, display a message to the user.
                    Log.w("EmailPassword", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        requireContext(),
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null)

                    val intent = Intent(requireActivity(), MainActivity::class.java)
                    startActivity(intent)
                }
            }
    }

    public fun outoConnect() {
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload()
        }
    }

    private fun reload() {
        auth.currentUser!!.reload().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val intent = Intent(requireActivity(), homepageActivity::class.java)
                startActivity(intent)
                Toast.makeText(requireContext(), "Reload successful!", Toast.LENGTH_SHORT).show()
            } else {
                Log.e(ContentValues.TAG, "reload", task.exception)
                Toast.makeText(requireContext(), "Failed to reload user.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUI(user : FirebaseUser?) {
        val intent = Intent(requireActivity(), homepageActivity::class.java)
        startActivity(intent)
    }
}