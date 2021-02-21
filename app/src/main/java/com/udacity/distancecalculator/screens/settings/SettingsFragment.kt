package com.udacity.distancecalculator.screens.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceScreen
import com.udacity.distancecalculator.R
import com.udacity.distancecalculator.preferences.getKeyByIndex
import com.udacity.distancecalculator.preferences.updateSummary
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {

    private var updateListener = getChangeListener()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        initializePreferenceSummaries()
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(updateListener)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(updateListener)
    }

    private fun initializePreferenceSummaries() {
        val prefScreen: PreferenceScreen = preferenceScreen
        val prefCount: Int = prefScreen.preferenceCount

        for (i in 0 until prefCount) {
            updateSummary(prefScreen.getKeyByIndex(i))
        }
    }

    private fun getChangeListener() =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            updateSummary(key)
        }
}