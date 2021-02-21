package com.udacity.distancecalculator.location

import android.annotation.SuppressLint
import android.location.Geocoder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.udacity.distancecalculator.cities.model.CityModel

class LocationProviderUtil(
    private val fusedLocationClient: FusedLocationProviderClient,
    private val geocoder: Geocoder
) {

    companion object {
        private const val DEFAULT_REQUEST_INTERVAL = 20 * 1000L
    }

    private lateinit var doOnSuccess: (CityModel) -> Unit

    fun doOnSuccess(doOnSuccess: (CityModel) -> Unit): LocationProviderUtil {
        this.doOnSuccess = doOnSuccess
        return this
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation() {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY;
            interval = DEFAULT_REQUEST_INTERVAL
        }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let {
                    val currentPlace = geocoder.getClosestAddress(it)?.toCityModel()
                    currentPlace?.let { doOnSuccess.invoke(currentPlace) }
                    fusedLocationClient.removeLocationUpdates(this)
                }
            }
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }
}