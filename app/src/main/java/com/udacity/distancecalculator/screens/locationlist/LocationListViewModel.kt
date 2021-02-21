package com.udacity.distancecalculator.screens.locationlist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.distancecalculator.cities.model.CityModel
import com.udacity.distancecalculator.cities.repository.CitiesRepository
import com.udacity.distancecalculator.common.coroutines.IO
import com.udacity.distancecalculator.common.text.containsLowercase
import kotlinx.coroutines.launch

class LocationListViewModel @ViewModelInject constructor(
    private val citiesRepository: CitiesRepository
) : ViewModel() {

    private var allLocations: List<CityModel>? = listOf()

    private val _visibleCities = MutableLiveData<List<CityModel>>()
    val visibleCities: LiveData<List<CityModel>> get() = _visibleCities

    fun loadAllItems() {
        viewModelScope.launch {
            IO { citiesRepository.fetchCountriesList() }
            val locationList = citiesRepository.citiesList.value
            _visibleCities.value = locationList
            allLocations = locationList
        }
    }

    fun doOnSearch(searchText: String) {
        val filteredList = allLocations
            ?.filter { it.city.containsLowercase(searchText) }
            ?.sortedBy { it.city }
        _visibleCities.postValue(filteredList)
    }

    fun doOnReset() {
        _visibleCities.postValue(allLocations)
    }
}