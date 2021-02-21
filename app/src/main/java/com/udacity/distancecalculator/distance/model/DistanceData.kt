package com.udacity.distancecalculator.distance.model

import com.udacity.distancecalculator.common.text.appendLocationPoint
import com.udacity.distancecalculator.common.text.appendSemiColon

data class DistanceData(
    val start: LocationPoint,
    val destination: LocationPoint
) {

    override fun toString(): String {
        return StringBuilder().apply {
            appendLocationPoint(start)
            appendSemiColon()
            appendLocationPoint(destination)
        }.toString()
    }

}