package com.udacity.distancecalculator.screens.selector.form

import com.udacity.distancecalculator.cities.model.CityModel


sealed class FormResult {

    class Complete(val start: CityModel, val destination: CityModel) : FormResult()

    object Incomplete : FormResult()

}