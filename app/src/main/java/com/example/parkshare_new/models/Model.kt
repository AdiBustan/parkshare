package com.example.parkshare_new.models

class Model private constructor(){

    val parkingLots: MutableList<Parking> = ArrayList()

    companion object {
        val instance: Model = Model()
    }
}