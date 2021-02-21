package com.udacity.distancecalculator.preferences

import android.content.Context
import com.udacity.distancecalculator.R
import com.udacity.distancecalculator.common.collections.firstOr

enum class DistanceUnit(
    val resId: Int,
    val unit: String,
    val ratio: Double
) {
    KILOMETERS(R.string.distance_unit_km_key, "km", 0.6215),
    MILES(R.string.distance_unit_mile_key, "mi", 1.609);

    companion object {

        fun getUnitByCode(context: Context, code: String): DistanceUnit {
            return values().firstOr(KILOMETERS) { code == context.getString(it.resId) }
        }

        fun convertToMile(currentValue: Double): Double {
            return currentValue.div(MILES.ratio)
        }
    }
}