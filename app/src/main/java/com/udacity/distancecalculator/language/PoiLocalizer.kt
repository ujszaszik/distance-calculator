package com.udacity.distancecalculator.language

import android.content.Context
import android.content.SharedPreferences
import com.google.android.gms.maps.model.PointOfInterest
import com.udacity.distancecalculator.R
import com.udacity.distancecalculator.common.text.replaceLineBreak
import com.udacity.distancecalculator.common.text.splitBySpaces
import com.udacity.distancecalculator.preferences.ApplicationLanguage
import com.udacity.distancecalculator.preferences.getStringById
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

class PoiLocalizer @Inject constructor(
    @ApplicationContext context: Context,
    sharedPreferences: SharedPreferences
) {

    private var currentLanguage: ApplicationLanguage

    init {
        val key = sharedPreferences.getStringById(context, R.string.language_preference_key)
        currentLanguage = ApplicationLanguage.getLanguage(key)
    }

    companion object {
        private const val HEBREW_MATCHER = ".*[\\u0590-\\u05ff]+.*"
        private const val ARABIC_MATCHER = ".*[\\u0600-\\u06ff]+.*"
    }

    fun getLocalizedName(poi: PointOfInterest): String {
        return when (currentLanguage) {
            ApplicationLanguage.HEBREW -> extractLocalizedName(poi, HEBREW_MATCHER)
            ApplicationLanguage.ARABIC -> extractLocalizedName(poi, ARABIC_MATCHER)
            else -> poi.name
        }
    }

    private fun extractLocalizedName(poi: PointOfInterest, matcher: String): String {
        val result = StringJoiner(" ")
        val split = poi.name.replaceLineBreak().splitBySpaces()
        split.forEach {
            if (it.matches(Regex(matcher))) result.add(it)
        }
        return result.toString()
    }
}