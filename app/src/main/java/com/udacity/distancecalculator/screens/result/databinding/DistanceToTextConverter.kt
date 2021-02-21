package com.udacity.distancecalculator.screens.result.databinding

import android.content.Context
import androidx.preference.PreferenceManager
import com.udacity.distancecalculator.R
import com.udacity.distancecalculator.preferences.DistanceUnit
import com.udacity.distancecalculator.preferences.getStringById
import java.math.BigDecimal
import java.math.RoundingMode

class DistanceToTextConverter(private val context: Context) {

    companion object {
        private const val MAX_DECIMAL_DIGITS = 2
        private const val METER_TO_KILOMETER_CONVERT_RATIO = 1000.0
    }

    fun getConvertedString(distanceInMeters: Double): String {
        val unitToUse = getUnitToUse()
        val isMile = unitToUse == DistanceUnit.MILES
        val distanceInKms = convertToKilometers(distanceInMeters)
        var distanceToShow = distanceInKms
        if (isMile) distanceToShow = DistanceUnit.convertToMile(distanceInKms)
        return convertToText(distanceToShow, unitToUse)
    }

    private fun getUnitToUse(): DistanceUnit {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val unitKey =
            sharedPreferences.getStringById(context, R.string.distance_unit_preference_key)
        return DistanceUnit.getUnitByCode(context, unitKey)
    }

    private fun convertToKilometers(distanceInMeters: Double): Double {
        return distanceInMeters / METER_TO_KILOMETER_CONVERT_RATIO
    }

    private fun roundToTwoDigits(number: Double): Double {
        return BigDecimal(number).setScale(MAX_DECIMAL_DIGITS, RoundingMode.HALF_EVEN).toDouble()
    }

    private fun convertToText(number: Double, unit: DistanceUnit): String {
        return "${roundToTwoDigits(number)} ${unit.unit}"
    }
}