package com.udacity.distancecalculator.screens.selector.cities

import android.text.Editable
import android.text.TextWatcher
import android.widget.AutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout
import com.udacity.distancecalculator.cities.model.CityModel
import com.udacity.distancecalculator.common.view.clear
import com.udacity.distancecalculator.screens.selector.extensions.performIconClick

class CitiesTextWatcher(
    private val layout: TextInputLayout,
    private val textView: AutoCompleteTextView,
    private val listener: CitiesAdapterListener
) : TextWatcher {

    override fun afterTextChanged(text: Editable?) {}

    override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
        listener.currentSelection.let {
            if (isProvidedLocationChanged(it, text) && isProvided() && isTextToWatch()) {
                layout.performIconClick()
                listener.currentSelection = null
                textView.clear()
            }
        }
    }

    private fun isProvidedLocationChanged(city: CityModel?, currentText: CharSequence?): Boolean {
        return city != null && city.isProvided() && city.city != currentText.toString()
    }

    private fun isProvided(): Boolean {
        val selectedCity: CityModel? = listener.currentSelection
        return selectedCity != null && selectedCity.isProvided()
    }

    private fun isTextToWatch(): Boolean {
        return textView.text.isNotEmpty()
    }

}