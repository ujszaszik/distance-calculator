package com.udacity.distancecalculator.screens.selector.cities

import android.view.View
import android.widget.AdapterView
import com.udacity.distancecalculator.cities.model.CityModel

class CitiesAdapterListener : AdapterView.OnItemClickListener {

    var currentSelection: CityModel? = null

    override fun onItemClick(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        val selectedCity = (parent?.getItemAtPosition(position) as CityModel)
        currentSelection = selectedCity
    }
}