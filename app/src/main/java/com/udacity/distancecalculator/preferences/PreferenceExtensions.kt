package com.udacity.distancecalculator.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceScreen
import com.udacity.distancecalculator.R

fun PreferenceScreen.getKeyByIndex(index: Int): String {
    val preference = getPreference(index) as ListPreference
    return preference.key
}

fun PreferenceFragmentCompat.updateSummary(key: String) {
    val preference = findPreference<Preference>(key) as ListPreference
    val index = preference.findIndexOfValue(preference.value)
    preference.summary = preference.entries[index]
}

fun SharedPreferences.getStringById(context: Context, resId: Int): String {
    return getString(context.getString(resId), "") ?: ""
}