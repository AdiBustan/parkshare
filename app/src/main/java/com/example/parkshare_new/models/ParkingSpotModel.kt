package com.example.parkshare_new.models

class ParkingSpotModel private constructor(){

    private val firebaseModel = FirebaseModel()

    companion object {
        val instance: ParkingSpotModel = ParkingSpotModel()
        const val PARKING_SPOTS_COLLECTION_PATH = "parkingSpot"
    }

    fun getAllParkingSpots(callback: (List<Parking>) -> Unit) {
        firebaseModel.getAllParkingSpots(callback)
    }

    fun addParking(parking: Parking, callback: () -> Unit) {
        firebaseModel.addDocument(PARKING_SPOTS_COLLECTION_PATH,
            parking.timestamp.toString(),
            parking.json,
            callback)
    }

    fun deleteParking(timestamp: Long, callback: () -> Unit) {
        firebaseModel.deleteDocument(PARKING_SPOTS_COLLECTION_PATH,
            timestamp.toString(),
            callback)
    }

    fun updateParkingSpot(parkingSpot: Parking, callback: () -> Unit) {
        firebaseModel.updateDocument(PARKING_SPOTS_COLLECTION_PATH,
            parkingSpot.timestamp.toString(),
            parkingSpot.json,
            callback)
    }
}