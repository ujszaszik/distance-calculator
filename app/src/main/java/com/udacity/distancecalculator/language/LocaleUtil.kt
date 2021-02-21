package com.udacity.distancecalculator.language

import android.content.Context
import androidx.preference.PreferenceManager
import com.udacity.distancecalculator.R
import com.udacity.distancecalculator.preferences.ApplicationLanguage
import com.udacity.distancecalculator.preferences.getStringById
import java.util.*

object LocaleUtil {

    fun isRtl(context: Context): Boolean {
        return context.getCurrentLocale()?.let {
            ApplicationLanguage.isRightToLeft(it.toString())
        } ?: false
    }

    fun updateLanguage(context: Context) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val currentLanguageText =
            sharedPreferences.getStringById(context, R.string.language_preference_key)
        val currentLanguageCode = ApplicationLanguage.getLocalCode(currentLanguageText)
        context.updateLanguageTo(Locale(currentLanguageCode))
    }
}