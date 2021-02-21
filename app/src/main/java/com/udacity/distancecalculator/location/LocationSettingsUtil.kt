package com.udacity.distancecalculator.location

import android.app.Activity
import android.content.IntentSender
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.SettingsClient

class LocationSettingsUtil(
    private val activity: Activity,
    private val settingsClient: SettingsClient,
    private val requestBuilder: LocationSettingsRequest.Builder
) {

    val requestCode = 29

    private val _isGranted = MutableLiveData<Boolean>()
    val isGranted: LiveData<Boolean> get() = _isGranted

    fun checkLocationSettings(resolve: Boolean = true) {
        settingsClient.checkLocationSettings(requestBuilder.build()).apply {
            addOnFailureListener { exception -> doOnFailure(resolve, exception) }
            addOnCompleteListener { if (it.isSuccessful) doOnSuccess() }
        }
    }

    private fun doOnFailure(resolve: Boolean, exception: Exception) {
        if (exception is ResolvableApiException && resolve) {
            try {
                startTurnOnLocationScreen(exception)
            } catch (sendEx: IntentSender.SendIntentException) {
                sendEx.printStackTrace()
            }
        } else {
            _isGranted.value = false
        }
    }

    private fun startTurnOnLocationScreen(exception: ResolvableApiException) {
        exception.startResolutionForResult(activity, requestCode)
    }

    private fun doOnSuccess() {
        _isGranted.postValue(true)
    }

    fun resetIfGranted() {
        _isGranted.value = null
    }

    fun updateOnReceivedBroadcast(value: Boolean) {
        _isGranted.postValue(value)
    }

}