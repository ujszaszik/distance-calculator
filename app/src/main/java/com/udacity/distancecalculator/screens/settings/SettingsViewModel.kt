package com.udacity.distancecalculator.screens.settings

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.distancecalculator.common.livedata.postTrue

class SettingsViewModel @ViewModelInject constructor() : ViewModel() {

    private val _isHomeScreenRequested = MutableLiveData<Boolean>()
    val isHomeScreenRequested: LiveData<Boolean> get() = _isHomeScreenRequested

    fun onHomeScreenRequest() {
        _isHomeScreenRequested.postTrue()
    }

    fun resetHomeScreenRequest() {
        _isHomeScreenRequested.value = null
    }
}