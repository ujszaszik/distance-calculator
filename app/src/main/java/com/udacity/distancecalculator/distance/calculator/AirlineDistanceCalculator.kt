package com.udacity.distancecalculator.distance.calculator

import com.udacity.distancecalculator.distance.model.DistanceData
import kotlin.math.*

object AirlineDistanceCalculator {

    private const val earthRadiusKm: Double = 6372.8
    private const val conversionRate = 1000

    /**
     * Using Haversine's algorithm
     */
    fun getDistance(data: DistanceData): Double {
        val latitudeDistance = getRadianDistanceOfLatitudes(data)
        val longitudeDistance = getRadianDistanceOfLongitudes(data)
        val originalLatitude = getRadianOriginalLatitude(data)
        val destinationLatitude = getRadianDestinationLatitude(data)

        val a = sin(latitudeDistance.half()).squared() +
                sin(longitudeDistance.half()).squared() *
                cos(originalLatitude) * cos(destinationLatitude)
        val c = 2 * asin(sqrt(a));

        return earthRadiusKm * c * conversionRate;
    }

    private fun getRadianDistanceOfLatitudes(data: DistanceData): Double {
        return Math.toRadians(data.destination.latitude - data.start.latitude)
    }

    private fun getRadianDistanceOfLongitudes(data: DistanceData): Double {
        return Math.toRadians(data.destination.longitude - data.start.longitude)
    }

    private fun getRadianOriginalLatitude(data: DistanceData): Double {
        return Math.toRadians(data.start.latitude)
    }

    private fun getRadianDestinationLatitude(data: DistanceData): Double {
        return Math.toRadians(data.destination.latitude)
    }

    private fun Double.half(): Double = this / 2

    private fun Double.squared(): Double = this.pow(2)

}