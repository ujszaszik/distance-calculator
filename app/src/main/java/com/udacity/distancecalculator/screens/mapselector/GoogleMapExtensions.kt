package com.udacity.distancecalculator.screens.mapselector

import android.content.Context
import android.graphics.Color
import android.location.Address
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.udacity.distancecalculator.R
import com.udacity.distancecalculator.cities.model.CityModel

fun Address.toLatLng(): LatLng = LatLng(latitude, longitude)

const val LOCATION_ZOOM = 15f
fun GoogleMap.moveCameraTo(latLng: LatLng, zoomLevel: Float = LOCATION_ZOOM) = moveCamera(
    CameraUpdateFactory.newLatLngZoom(latLng, LOCATION_ZOOM)
)

const val DEFAULT_ZOOM = 10f
const val GOOGLEPLEX_LATITUDE = 37.42232119840764
const val GOOGLEPLEX_LONGITUDE = -122.08418626149322

fun GoogleMap.moveCameraToDefault() {
    LatLng(GOOGLEPLEX_LATITUDE, GOOGLEPLEX_LONGITUDE).run {
        moveCameraTo(this, DEFAULT_ZOOM)
    }
}

fun GoogleMap.fitZoomToMarkers(list: List<LatLng>) {
    val bounds = LatLngBounds.Builder().run {
        list.forEach { current -> this.include(current) }
        build()
    }
    animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50))
}

fun PointOfInterest.toMarkerOptions(): MarkerOptions {
    return MarkerOptions().position(latLng).title(name)
}

fun PointOfInterest.toStartMarkerOptions(context: Context): MarkerOptions {
    return toMarkerOptions().icon(context.getMarkerIcon(R.color.green))
}

fun PointOfInterest.toDestinationMarkerOptions(context: Context): MarkerOptions {
    return toMarkerOptions().icon(context.getMarkerIcon(R.color.yellow))
}

fun PointOfInterest.toCityModel(): CityModel {
    return CityModel(name, "", latLng.latitude, latLng.longitude)
}

fun Context.getMarkerIcon(colorId: Int): BitmapDescriptor? {
    val hsv = FloatArray(3)
    Color.colorToHSV(getColor(colorId), hsv)
    return BitmapDescriptorFactory.defaultMarker(hsv[0])
}