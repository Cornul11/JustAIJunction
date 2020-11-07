package com.justai.jaicf.template

import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class LocationHandler {
    private val known_places = listOf("Der aa Kerk", "University of Groningen", "Martinikerk", "Martinitoren", "Groninger Museum")
    private val location_history = mutableListOf<String>()

    fun getLocationHistory() : MutableList<String> {
        return location_history
    }

    fun getPlaceName(latitude: Double, longitude: Double): String {
        val url =
                "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=$latitude,$longitude&radius=20&key=AIzaSyC9umGSBv04JS9H1mNoIUdzf8o8e_IQ_nw"
        val request = okhttp3.Request.Builder().url(url).build()

        val client = OkHttpClient()
        val place_list = mutableListOf<Pair<String, MutableList<String>>>()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: okhttp3.Response) {
                val places = JSONArray(JSONObject(response.body()?.string()).get("results").toString())
                for (i in 0 until places.length()) {
                    val name = JSONObject(places[i].toString()).get("name").toString()
                    val typesArray =
                            (JSONObject(places[i].toString()).get("types") as JSONArray)
                    val types = mutableListOf<String>()
                    for (j in 0 until typesArray.length()) {
                        types.add(typesArray[j].toString())
                    }
                    place_list.add(Pair(name, types))
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("FAILED TO EXECUTE REQUEST")
            }
        })
        // Sleep for now, maybe add async/await later
        Thread.sleep(1000L)

        for (place in place_list) {
            if (known_places.contains(place.first)) {
                location_history.add(place.first)
                return place.first
            }
        }
        return ""
    }

    fun getCity(latitude: Double, longitude: Double): String {
        val url =
                "https://maps.googleapis.com/maps/api/geocode/json?latlng=$latitude,$longitude&result_type=administrative_area_level_1&key=AIzaSyC9umGSBv04JS9H1mNoIUdzf8o8e_IQ_nw"
        val request = okhttp3.Request.Builder().url(url).build()
        val client = OkHttpClient()
        var city_name = ""

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: okhttp3.Response) {
                val results = JSONArray(JSONObject(response.body()?.string()).get("results").toString())[0].toString()
                val address_component = JSONArray(JSONObject(results).get("address_components").toString())[0].toString()
                city_name = JSONObject(address_component).get("long_name").toString()
            }

            override fun onFailure(call: Call, e: IOException) {
                println("FAILED TO EXECUTE REQUEST")
            }
        })
        // Sleep for now, maybe add async/await later
        Thread.sleep(1000L)

        return city_name
    }
}
