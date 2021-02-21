package com.udacity.distancecalculator.location

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager

class LocationChangedReceiver(private val locationSettingsUtil: LocationSettingsUtil) :
    BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val isStateChanged = LocationManager.PROVIDERS_CHANGED_ACTION == intent.action
        if (isStateChanged) {
            val locationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            locationSettingsUtil.updateOnReceivedBroadcast(isGpsEnabled)
        }
    }

}