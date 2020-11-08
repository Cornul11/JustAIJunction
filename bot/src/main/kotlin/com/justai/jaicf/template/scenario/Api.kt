package com.justai.jaicf.template.scenario


import kotlinx.coroutines.runBlocking
import java.net.URL
import 	org.json.JSONObject

object Api {

    /**
     * get travel history
     */
    private var location_history = mutableListOf<String>()
    fun getLocationHistory() : List<String> {
        return location_history
    }

    /**
     *  retrieve information (summary) from wikipedia api
     */
    fun getWikiInfo(loc: String) = runBlocking {
        val normalizedValue = normalizeValue(loc)
        val query = JSONObject(URL("https://en.wikipedia.org/w/api.php?format=json&action=query&prop=" +
                "extracts&exintro&explaintext&redirects=1&titles=$normalizedValue").readText()).get("query")
        val q = query.toString().split(Regex(":"), 3)
        if (!location_history.contains(loc)) {
            location_history.add(loc)
        }
        JSONObject(q[2].substring(0, q[2].length - 2)).get("extract").toString()
    }

    private fun normalizeValue(value: String) : String{
        if (value == "University")
            return "University+of+Groningen"
        if (value == "Martinikerk")
            return "Martinikerk_(Groningen)"
        if (value == "Der aa Kerk")
            return "Der_Aa-kerk"
        if (value == "Groninger Museum")
            return "Groninger_Museum"
        if (value == "Martinitoren")
            return "Martinitoren"
        return "Groningen"
    }

}