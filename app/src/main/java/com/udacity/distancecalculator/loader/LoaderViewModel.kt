package com.udacity.distancecalculator.loader

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoaderViewModel @ViewModelInject constructor() : ViewModel() {

    private val _navigateToMainScreen = MutableLiveData<Boolean>()
    val navigateToMainScreen: LiveData<Boolean> get() = _navigateToMainScreen

    fun startLoading() {
        viewModelScope.launch {
            delay(3000L)
            _navigateToMainScreen.postValue(true)
        }
    }

    fun resetNavigation() {
        _navigateToMainScreen.postValue(false)
    }
}