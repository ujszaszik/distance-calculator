package com.udacity.distancecalculator.screens.selector

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.distancecalculator.cities.model.CityModel
import com.udacity.distancecalculator.cities.repository.CitiesRepository
import com.udacity.distancecalculator.common.coroutines.IO
import com.udacity.distancecalculator.common.livedata.postTrue
import com.udacity.distancecalculator.common.network.NetworkUtil
import com.udacity.distancecalculator.screens.selector.form.FormResult
import kotlinx.coroutines.launch

class SelectorViewModel @ViewModelInject constructor(
    private val citiesRepository: CitiesRepository
) : ViewModel() {

    val citiesList = citiesRepository.citiesList

    private val _formResult = MutableLiveData<FormResult>()
    val formResult: LiveData<FormResult> get() = _formResult

    private val _isSettingsPageRequested = MutableLiveData<Boolean>()
    val isSettingsPageRequested: LiveData<Boolean> get() = _isSettingsPageRequested

    private val _isOpenMapScreenRequested = MutableLiveData<Boolean>()
    val isOpenMapScreenRequested: LiveData<Boolean> get() = _isOpenMapScreenRequested

    private val _isUnableToFetchCities = MutableLiveData<Boolean>()
    val isUnableToFetchCities: LiveData<Boolean> get() = _isUnableToFetchCities

    init {
        viewModelScope.launch { loadCities() }
    }

    private suspend fun loadCities() {
        when {
            isUnableToFetchCities() -> _isUnableToFetchCities.postTrue()
            isDisconnectedButAlreadyFetched() -> loadCitiesFromLocalDatabase()
            else -> fetchRemoteCities()
        }
    }

    private fun isUnableToFetchCities() =
        !NetworkUtil.isConnected && !citiesRepository.isAlreadyFetched()

    private fun isDisconnectedButAlreadyFetched() =
        !NetworkUtil.isConnected && citiesRepository.isAlreadyFetched()

    private suspend fun fetchRemoteCities() {
        IO {
            citiesRepository.cacheRemoteCities()
            loadCitiesFromLocalDatabase()
        }
    }

    private suspend fun loadCitiesFromLocalDatabase() {
        citiesRepository.fetchCountriesList()
    }

    fun validateFields(start: CityModel?, destination: CityModel?) {
        viewModelScope.launch {
            if (isIncompleteForm(start, destination)) {
                _formResult.postValue(FormResult.Incomplete)
            } else {
                _formResult.postValue(FormResult.Complete(start!!, destination!!))
            }
        }
    }

    private fun isIncompleteForm(start: CityModel?, destination: CityModel?): Boolean {
        return start == null || start.isEmpty() ||
                destination == null || destination.isEmpty()
    }

    fun onSettingsScreenRequest() {
        _isSettingsPageRequested.postTrue()
    }

    fun onOpenMapRequested() {
        _isOpenMapScreenRequested.postTrue()
    }

    fun resetFormState() {
        _formResult.value = null
    }

    fun resetSettingsRequest() {
        _isSettingsPageRequested.value = null
    }

    fun resetMapRequest() {
        _isOpenMapScreenRequested.value = null
    }

    fun resetDisconnectedEvent() {
        _isUnableToFetchCities.value = null
    }

}