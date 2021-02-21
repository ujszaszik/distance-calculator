package com.udacity.distancecalculator.distance.model

import com.udacity.distancecalculator.common.text.appendColon
import com.udacity.distancecalculator.common.text.appendDouble

data class LocationPoint(
    val latitude: Double,
    val longitude: Double
) {

    override fun toString(): String {
        return StringBuilder().apply {
            appendDouble(longitude)
            appendColon()
            appendDouble(latitude)
        }.toString()
    }
}