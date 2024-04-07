package com.example.parkshare_new.services

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import com.example.parkshare_new.R
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ImagesService {

    companion object {

        fun loadingImageFromStorage(imageView: ImageView, imageName: String?) {
            if (imageName?.isNotEmpty() == true) {
                val storageReference = FirebaseStorage.getInstance().reference.child("/images/$imageName")

                val localFile = File.createTempFile("tempImage", "jpg")
                storageReference.getFile(localFile).addOnSuccessListener {
                    Picasso.get().load(localFile).into(imageView)

                }.addOnFailureListener {
                    Log.d("Tag", "Faild To Load Image ")
                }
            } else {
                Picasso.get().load(R.drawable.loading).into(imageView)
            }
        }

        fun uploadImageToStorage(selectedImageURI: Uri): String {
            val formatter = SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.FRANCE)
            val now = Date()
            val fileName = formatter.format(now)
            val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")

            storageReference.putFile(selectedImageURI)

            return fileName
        }
    }

}