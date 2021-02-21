package com.udacity.distancecalculator.location

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class LocationPermissionUtil(private val activity: Activity) {

    private val _isGranted = MutableLiveData<Boolean>()
    val isGranted: LiveData<Boolean> get() = _isGranted

    fun requestPermission() {
        var permissionsArray = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissionsArray += Manifest.permission.ACCESS_BACKGROUND_LOCATION
        }

        ActivityCompat.requestPermissions(
            activity,
            permissionsArray,
            LocationRequestCode.forCurrentApiLevel()
        )
    }

    fun isGranted(): Boolean {
        var result: Boolean = isForegroundPermissionGranted()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            result = result && isBackgroundPermissionGranted()
        }
        return result
    }

    private fun isForegroundPermissionGranted(): Boolean {
        return PackageManager.PERMISSION_GRANTED ==
                ActivityCompat
                    .checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun isBackgroundPermissionGranted(): Boolean {
        return PackageManager.PERMISSION_GRANTED ==
                ActivityCompat
                    .checkSelfPermission(activity, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
    }

    fun updateGrantState(value: Boolean) = _isGranted.postValue(value)

    fun resetGrantState() {
        _isGranted.value = null
    }

}