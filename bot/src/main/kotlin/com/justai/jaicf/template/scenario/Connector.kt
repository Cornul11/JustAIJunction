package com.justai.jaicf.template.scenario


import kotlinx.coroutines.runBlocking
import org.json.JSONArray
import java.net.URL
import 	org.json.JSONObject

object Connector {

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

    fun getWikiCityHistoryInfo(city: String) = runBlocking {
        val query = JSONObject(URL("https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&explaintext&exsectionformat=plain&titles=$city&redirects=").readText()).get("query").toString()
        var startIndex = query.indexOf("\\n\\n\\nHistory", 0)
        startIndex += 15
        val stopIndex = query.indexOf("\\n\\n\\n", startIndex)
        query.substring(startIndex, stopIndex)
    }

    fun getWikiCityCultureInfo(city: String) = runBlocking {
        val query = JSONObject(URL("https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&explaintext&exsectionformat=plain&titles=$city&redirects=").readText()).get("query").toString()
        var startIndex = query.indexOf("\\n\\n\\nCulture", 0)
        startIndex += 15
        val stopIndex = query.indexOf("\\n\\n\\n", startIndex)
        query.substring(startIndex, stopIndex)
    }

    fun getWikiCityPoliticsInfo(city: String) = runBlocking {
        val query = JSONObject(URL("https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&explaintext&exsectionformat=plain&titles=$city&redirects=").readText()).get("query").toString()
        var startIndex = query.indexOf("\\n\\n\\nPolitics", 0)
        startIndex += 16
        val stopIndex = query.indexOf("\\n\\n\\n", startIndex)
        query.substring(startIndex, stopIndex)
    }

    fun getCity(latitude: Double, longitude: Double): String {
        val url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=$latitude,$longitude&result_type" +
                "=administrative_area_level_1&key=AIzaSyC9umGSBv04JS9H1mNoIUdzf8o8e_IQ_nw"
        val results = JSONArray(JSONObject(URL(url).readText()).get("results").toString())[0].toString()
        val address_component = JSONArray(JSONObject(results).get("address_components").toString())[0].toString()
        return JSONObject(address_component).get("long_name").toString()
    }

}
