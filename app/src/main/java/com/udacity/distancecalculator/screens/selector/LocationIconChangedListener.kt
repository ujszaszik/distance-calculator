package com.udacity.distancecalculator.screens.selector

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.internal.CheckableImageButton
import com.udacity.distancecalculator.R
import com.udacity.distancecalculator.cities.model.CityModel
import com.udacity.distancecalculator.location.LocationProviderUtil

class LocationIconChangedListener(
    private val context: Context,
    private val locationProviderUtil: LocationProviderUtil,
    private val onLocationProvided: (CityModel) -> Unit,
    private val onChecked: () -> Unit,
    private val onUnchecked: () -> Unit
) :
    View.OnClickListener {

    private var isChecked = false

    override fun onClick(view: View) {
        val imageButton = view as CheckableImageButton
        if (isChecked) {
            doWhenUnchecked(imageButton)
        } else {
            locationProviderUtil
                .doOnSuccess { doWhenChecked(imageButton, it) }
                .getCurrentLocation()
        }
    }

    private fun doWhenUnchecked(imageButton: CheckableImageButton) {
        changeIconTo(imageButton, R.drawable.ic_location_black)
        onUnchecked.invoke()
        isChecked = false
    }

    private fun doWhenChecked(imageButton: CheckableImageButton, cityModel: CityModel) {
        changeIconTo(imageButton, R.drawable.ic_location_green)
        onLocationProvided.invoke(cityModel)
        onChecked.invoke()
        isChecked = true
    }

    private fun changeIconTo(imageButton: CheckableImageButton, iconId: Int) {
        imageButton.setImageDrawable(ContextCompat.getDrawable(context, iconId))
    }
}