package com.example.parkshare_new.models

class UserModel private constructor(){

    private val firebaseModel = FirebaseModel()


    companion object {
        val instance: UserModel = UserModel()
        const val PROFILES_COLLECTION_PATH = "profiles"
    }

    fun getAllParkingSpotsByUser(email: String, callback: (List<Parking>) -> Unit) {
        firebaseModel.getAllParkingSpotsByUser(email, callback)
    }

    fun addUser(profile: Profile, callback: () -> Unit) {
        firebaseModel.addDocument(PROFILES_COLLECTION_PATH,
            profile.email,
            profile.json,
            callback)
    }

    fun updateUserDetails(profile: Profile, callback: () -> Unit) {
        val updatedData = hashMapOf<String, Any>(
            "userName" to profile.userName,
            "faveCity" to profile.faveCity,
            "avatar" to profile.avatar
        )

        firebaseModel.updateDocument(PROFILES_COLLECTION_PATH,
            profile.email,
            updatedData,
            callback)
    }

    fun getUserByEmail(email: String, callback: (Profile?) -> Unit) {
        firebaseModel.getUserByEmail(email, callback)
    }
}