package com.udacity.distancecalculator.screens.result

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.distancecalculator.common.coroutines.IO
import com.udacity.distancecalculator.distance.model.DistanceData
import com.udacity.distancecalculator.distance.repository.DistanceRepository
import kotlinx.coroutines.launch

class ResultViewModel @ViewModelInject constructor(
    private val distanceRepository: DistanceRepository
) : ViewModel() {

    val distanceResult = distanceRepository.distanceResult

    val loadingStatus = distanceRepository.distanceResultLoadingStatus

    fun calculate(data: DistanceData) {
        viewModelScope.launch {
            IO { distanceRepository.calculateDistances(data) }
        }
    }

    fun calculateOffline(data: DistanceData) {
        viewModelScope.launch {
            IO { distanceRepository.calculateOffline(data) }
        }
    }

    fun resetDistanceResult() {
        distanceRepository.resetState()
    }

}