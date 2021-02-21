package com.udacity.distancecalculator.language

import android.content.Context
import android.content.res.Resources
import android.os.LocaleList
import com.udacity.distancecalculator.App
import java.util.*

private fun LocaleList.first() = get(0)

@Suppress("DEPRECATION")
fun Context.getCurrentLocale(): Locale? {
    val configuration = resources.configuration
    return if (App.isRunningOnAtLeastN()) configuration.locales.first()
    else configuration.locale
}


fun Context.updateLanguageTo(locale: Locale) {
    with(locale) {
        Locale.setDefault(this)
        resources.updateLocaleTo(this)
    }
}

@Suppress("DEPRECATION")
fun Resources.updateLocaleTo(locale: Locale) {
    configuration.setLocale(locale)
    updateConfiguration(configuration, displayMetrics)
}