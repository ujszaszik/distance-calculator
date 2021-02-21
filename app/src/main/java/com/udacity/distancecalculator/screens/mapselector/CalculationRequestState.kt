package com.udacity.distancecalculator.screens.mapselector

import com.udacity.distancecalculator.cities.model.CityModel

sealed class CalculationRequestState {

    class Valid(private val startingPoint: CityModel, private val destination: CityModel) :
        CalculationRequestState() {

        operator fun component1() = startingPoint

        operator fun component2() = destination
    }

    object Incomplete : CalculationRequestState()
}