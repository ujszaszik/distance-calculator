package com.udacity.distancecalculator.cities.repository

import com.udacity.distancecalculator.cities.model.CityModel
import org.json.JSONArray
import org.json.JSONObject

object CitiesResultParser {

    private const val ROUTES_KEY = "routes"
    private const val DISTANCE_KEY = "distance"
    private const val DURATION_KEY = "duration"
    private const val CODE_KEY = "code"
    private const val NO_ROUTE_MESSAGE = "NoRoute"

    fun getCities(apiResult: Map<*, *>): List<CityModel> {
        val json = JSONObject(apiResult)
        val array = json.getJSONArray(ROUTES_KEY).get(0) as JSONArray
        val list = mutableListOf<CityModel>()
        for (i in 0..array.length()) {
            val current = array.getJSONObject(i)
            val city = current.get("city") as String
            val country = current.get("country") as String
            val latitude = current.get("latitude") as Double
            val longitude = current.get("longitude") as Double
            list.add(CityModel(city, country, latitude, longitude))
        }
        return list
    }
}