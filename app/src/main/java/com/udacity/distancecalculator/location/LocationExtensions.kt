package com.udacity.distancecalculator.location

import android.location.Address
import android.location.Geocoder
import android.location.Location
import com.udacity.distancecalculator.cities.model.CityModel
import com.udacity.distancecalculator.cities.model.LocationModelType
import java.io.IOException

fun Geocoder.getClosestAddress(location: Location): Address? {
    return try {
        getFromLocation(location.latitude, location.longitude, 1).first()
    } catch (e: IOException) {
        null
    }
}

fun Address.toCityModel(): CityModel {
    val currentPlace = "$locality, $thoroughfare"
    return CityModel(currentPlace, countryCode, latitude, longitude, LocationModelType.PROVIDED)
}