package com.justai.jaicf.template.scenario


import kotlinx.coroutines.runBlocking
import java.net.URL
import 	org.json.JSONObject

object Api {

    /**
     *  retrieve information (summary) from wikipedia api
     */
    fun getWikiInfo(loc: String) = runBlocking {
        val normalizedValue = normalizeValue(loc)
        val query = JSONObject(URL("https://en.wikipedia.org/w/api.php?format=json&action=query&prop=" +
                "extracts&exintro&explaintext&redirects=1&titles=$normalizedValue").readText()).get("query")
        val q = query.toString().split(Regex(":"), 3)
        JSONObject(q[2].substring(0, q[2].length - 2)).get("extract").toString()
    }

    fun getWikiCityHistoryInfo(city: String) = runBlocking {
        val query = JSONObject(URL("https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&explaintext&exsectionformat=plain&titles=$city&redirects=").readText()).get("query").toString()
        var startIndex = query.indexOf("\\n\\n\\nHistory", 0)
        startIndex += 15
        var stopIndex = query.indexOf("\\n\\n\\n", startIndex)
        query.substring(startIndex, stopIndex)
    }

    fun getWikiCityCultureInfo(city: String) = runBlocking {
        ""
    }

    fun getWikiCityPoliticsInfo(city: String) = runBlocking {
        ""
    }

    private fun normalizeValue(value: String) : String{
        if (value == "University of Groningen")
            return  "University+of+Groningen"
        if (value == "Martinikerk")
            return  "Martinikerk_(Groningen)"
        if (value == "Der aa Kerk")
            return  "Der_Aa-kerk"
        if (value == "Groninger Museum")
            return "Groninger_Museum"
        if (value == "Martinitoren")
            return "Martinitoren"
        return "Groningen"
    }

}