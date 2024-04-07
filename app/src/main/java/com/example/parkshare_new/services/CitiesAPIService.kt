package com.example.parkshare_new.services

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Spinner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class CitiesAPIService {

    companion object {

        fun fetchCitiesInIsraelFromAPI(context: Context, citySpinner: Spinner, defaultVal: String) {
            GlobalScope.launch(Dispatchers.IO) {
                val url = URL("https://countriesnow.space/api/v0.1/countries")
                val connection = url.openConnection() as HttpURLConnection
                val response = StringBuilder()
                try {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                    }
                    reader.close()
                } finally {
                    connection.disconnect()
                }

                // Parse JSON response to extract the list of cities
                val cities = parseCitiesFromJSON(response.toString(), defaultVal)

                // Update UI with the list of cities
                withContext(Dispatchers.Main) {
                    updateCitySpinner(cities, context, citySpinner)
                }
            }
        }

        private fun parseCitiesFromJSON(json: String, defaultVal: String): List<String> {
            val cities = mutableListOf<String>()
            cities.add(defaultVal)

            val allJsonObject = JSONObject(json)
            val jsonArray = JSONArray(allJsonObject.get("data").toString())

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val country = jsonObject.getString("country")
                if (country == "Israel") {
                    val citiesArray = jsonObject.getJSONArray("cities")
                    for (j in 0 until citiesArray.length()) {
                        val city = citiesArray.getString(j)
                        cities.add(city)
                    }
                    break  // Stop loop after finding Israel
                }
            }

            return cities
        }

        private fun updateCitySpinner(cities: List<String>, context: Context, citySpinner: Spinner) {
            val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, cities)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            citySpinner.adapter = adapter
        }
    }
}