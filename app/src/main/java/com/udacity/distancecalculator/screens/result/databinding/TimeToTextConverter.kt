package com.udacity.distancecalculator.screens.result.databinding

object TimeToTextConverter {

    private const val DAYS_TEXT = "d "
    private const val HOURS_TEXT = "h "
    private const val MINUTES_TEXT = "m "
    private const val SECONDS_TEXT = "s"

    fun convertToString(timeInSecs: Double): String {
        val roundedValue = timeInSecs.toInt()
        val seconds = roundedValue % 60
        val minutes = ((roundedValue / 60) % 60)
        val hours = ((roundedValue / 3600) % 24)
        val days = (roundedValue / 86400)
        return createTimeString(hours, minutes, seconds, days)
    }

    private fun createTimeString(hours: Int, minutes: Int, seconds: Int, days: Int): String {
        return StringBuilder().apply {
            if (days != 0) append(days).append(DAYS_TEXT)
            if (hours != 0) append(hours).append(HOURS_TEXT)
            if (minutes != 0) append(minutes).append(MINUTES_TEXT)
            if (seconds != 0) append(seconds).append(SECONDS_TEXT)
        }.toString()
    }
}