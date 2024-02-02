package com.example.parkshare_new.models

class Model private constructor(){

    val parkingLots: MutableList<Parking> = ArrayList()

    companion object {
        val instance: Model = Model()
    }

    init {
        for (i in 0..20) {
            var parking = Parking("avatar: https://me.com/avatar.jpg",
                "address $i",
                "name: $i",
                false)
            parkingLots.add(parking)
        }
    }
}