package com.justai.jaicf.template.scenario


import kotlinx.coroutines.runBlocking
import java.net.URL
import 	org.json.JSONObject

object Api {

    /**
     *  retrieve information (summary) from wikipedia api
     */
    fun getWikiInfo(loc: String) = runBlocking {
        val query = JSONObject(URL("https://en.wikipedia.org/w/api.php?format=json&action=query&prop=" +
                "extracts&exintro&explaintext&redirects=1&titles=$loc").readText()).get("query")
        val q = query.toString().split(Regex(":"), 3)
        JSONObject(q[2].substring(0, q[2].length - 2)).get("extract").toString()
    }
}