package com.udacity.distancecalculator.screens.mapselector.search

import android.content.Context
import android.location.Geocoder
import androidx.lifecycle.Observer
import com.google.android.gms.maps.GoogleMap
import com.udacity.distancecalculator.screens.mapselector.moveCameraTo
import com.udacity.distancecalculator.screens.mapselector.toLatLng


class MapTextSearchObserver<T : String?>(
    private val geocoder: Geocoder,
    private val map: GoogleMap,
    private val context: Context
) : Observer<T> {

    override fun onChanged(text: T?) {
        if (!text.isNullOrEmpty()) {
            val addresses = geocoder.getFromLocationName(text, 1)
            if (addresses.isNotEmpty()) map.moveCameraTo(addresses.first().toLatLng())
            else context.showNotFoundToast()
        } else {
            context.showEmptySearchToast()
        }
    }


}