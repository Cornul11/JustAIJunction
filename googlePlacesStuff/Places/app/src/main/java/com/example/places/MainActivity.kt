package com.example.places

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getPos(53.216700,6.571982)
    }

    fun getPos(latitude: Double, longitude: Double): String {
        val url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + latitude.toString() + "," + longitude.toString() + "" +
                                                                                       "&radius=20&key=AIzaSyC9umGSBv04JS9H1mNoIUdzf8o8e_IQ_nw"
        val request = okhttp3.Request.Builder().url(url).build()

        var client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: okhttp3.Response) {
                val body = response.body?.string()
                println(body)

                val results = JSONArray(JSONObject(body).get("results").toString())
                for (i in 0 until results.length()) {
                    print(i)
                    print(" ")
                    println(JSONObject(results[i].toString()).get("name"))
                    println(JSONObject(results[i].toString()).get("types"))
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("FAILED TO EXECUTE REQUEST")
            }
        })
        return ""
    }
}

