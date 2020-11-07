package com.example.places

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import kotlinx.coroutines.*
import kotlin.system.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println(getPos(53.219351, 6.567878))
    }

    fun getPos(latitude: Double, longitude: Double): List<Pair<String, MutableList<String>>> {
        val url =
            "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=$latitude,$longitude&radius=20&key=AIzaSyC9umGSBv04JS9H1mNoIUdzf8o8e_IQ_nw"
        val request = okhttp3.Request.Builder().url(url).build()

        var client = OkHttpClient()
        val result = mutableListOf<Pair<String, MutableList<String>>>()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: okhttp3.Response) {
                val places = JSONArray(JSONObject(response.body?.string()).get("results").toString())
                for (i in 0 until places.length()) {
                    val name = JSONObject(places[i].toString()).get("name").toString()
                    val typesArray =
                        (JSONObject(places[i].toString()).get("types") as JSONArray)
                    val types = mutableListOf<String>()
                    for (j in 0 until typesArray.length()) {
                        types.add(typesArray[j].toString())
                    }
                    result.add(Pair(name, types))
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("FAILED TO EXECUTE REQUEST")
            }
        })
        // Sleep for now, maybe add async/await later
        Thread.sleep(2000L)
        return result
    }
}

