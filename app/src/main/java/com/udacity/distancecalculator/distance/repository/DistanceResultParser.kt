package com.udacity.distancecalculator.distance.repository

import com.udacity.distancecalculator.distance.model.DistanceModel
import org.json.JSONArray
import org.json.JSONObject

object DistanceResultParser {

    private const val FEATURES_KEY = "features"
    private const val PROPERTIES_KEY = "properties"
    private const val SUMMARY_KEY = "summary"
    private const val DISTANCE_KEY = "distance"
    private const val DURATION_KEY = "duration"

    fun parseDistance(apiResult: Map<*, *>): DistanceModel {
        return try {
            val json = JSONObject(apiResult)
            val features = json.getJSONArray(FEATURES_KEY).first()
            val properties = features.getJSONObject(PROPERTIES_KEY)
            val summary = properties.getJSONObject(SUMMARY_KEY)
            val distance = summary.get(DISTANCE_KEY) as Double
            val duration = summary.get(DURATION_KEY) as Double
            DistanceModel(distance, duration)
        } catch (e: Exception) {
            DistanceModel.noData()
        }
    }

    private fun JSONArray.first(): JSONObject = get(0) as JSONObject
}