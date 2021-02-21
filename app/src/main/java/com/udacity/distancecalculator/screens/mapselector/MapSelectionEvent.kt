package com.udacity.distancecalculator.screens.mapselector

import com.google.android.gms.maps.model.PointOfInterest

sealed class MapSelectionEvent {

    class Added(val poi: PointOfInterest) : MapSelectionEvent()

    object Removed : MapSelectionEvent()

}