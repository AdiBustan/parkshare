package com.example.parkshare_new.services

import retrofit2.http.GET

data class CountryResponse(
    val data: List<Country>
)

data class Country(
    val name: String,
    val cities: List<String>
)

interface CountriesApiService {
    @GET("countries")
    suspend fun getCountries(): CountryResponse
}