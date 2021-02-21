package com.udacity.distancecalculator.screens.selector.extensions

import android.widget.AutoCompleteTextView
import com.google.android.material.internal.CheckableImageButton
import com.google.android.material.textfield.TextInputLayout
import com.udacity.distancecalculator.R
import com.udacity.distancecalculator.cities.model.CityModel
import com.udacity.distancecalculator.common.view.clear
import com.udacity.distancecalculator.location.LocationProviderUtil
import com.udacity.distancecalculator.screens.selector.LocationIconChangedListener
import com.udacity.distancecalculator.screens.selector.cities.CitiesAdapterListener
import com.udacity.distancecalculator.screens.selector.cities.CitiesTextWatcher

fun TextInputLayout.hideEndIcon() {
    isEndIconVisible = false
}

fun TextInputLayout.showEndIcon() {
    isEndIconVisible = true
}

fun TextInputLayout.performIconClick() {
    val iconButton = findViewById<CheckableImageButton>(R.id.text_input_end_icon)
    iconButton.performClick()
}

fun TextInputLayout.setLocationIconListener(
    textView: AutoCompleteTextView,
    locationProviderUtil: LocationProviderUtil,
    listener: CitiesAdapterListener,
    onLocationProvided: (CityModel) -> Unit
) {
    setEndIconOnClickListener(
        LocationIconChangedListener(
            context,
            locationProviderUtil,
            onLocationProvided = onLocationProvided,
            onChecked = { },
            onUnchecked = { textView.clear().also { listener.currentSelection = null } }
        )
    )
}

fun AutoCompleteTextView.setCitiesTextWatcher(
    layout: TextInputLayout,
    textView: AutoCompleteTextView
) = addTextChangedListener(
    CitiesTextWatcher(
        layout,
        textView,
        onItemClickListener as CitiesAdapterListener
    )
)