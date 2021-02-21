package com.udacity.distancecalculator.screens.result

import com.udacity.distancecalculator.distance.model.LocationPoint

fun ResultFragmentArgs.getStartingLocation(): LocationPoint {
    return LocationPoint(startCity.latitude, startCity.longitude)
}

fun ResultFragmentArgs.getDestinationLocation(): LocationPoint {
    return LocationPoint(destinationCity.latitude, destinationCity.longitude)
}