package com.udacity.distancecalculator.screens.selector.cities

import android.widget.AutoCompleteTextView
import com.udacity.distancecalculator.cities.model.CityModel

class CityValidator(
    private val autoCompleteTextView: AutoCompleteTextView,
    private val citiesAdapterListener: CitiesAdapterListener
) :
    AutoCompleteTextView.Validator {
    override fun fixText(text: CharSequence?): CharSequence {
        return "".also { autoCompleteTextView.clearFocus() }
    }

    override fun isValid(text: CharSequence?): Boolean {
        val adapter = autoCompleteTextView.adapter as CitiesAdapter
        return adapter.list.any { it.city == text.toString() } || isValidProvided(text)
    }

    private fun isValidProvided(text: CharSequence?): Boolean {
        val selection = citiesAdapterListener.currentSelection ?: return false
        return isSameAsProvided(selection, text)
    }

    private fun isSameAsProvided(selection: CityModel, text: CharSequence?): Boolean {
        return selection.isProvided() && selection.city == text.toString()
    }
}