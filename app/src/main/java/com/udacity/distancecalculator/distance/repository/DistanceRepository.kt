package com.udacity.distancecalculator.distance.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.distancecalculator.common.coroutines.networkCall
import com.udacity.distancecalculator.common.network.LoadingStatus
import com.udacity.distancecalculator.distance.calculator.AirlineDistanceCalculator
import com.udacity.distancecalculator.distance.model.DistanceData
import com.udacity.distancecalculator.distance.model.DistanceModel
import com.udacity.distancecalculator.distance.model.DistanceResult
import com.udacity.distancecalculator.distance.network.DistanceService
import com.udacity.distancecalculator.distance.network.DistanceServiceScope
import com.udacity.distancecalculator.keys.Keys
import javax.inject.Inject

class DistanceRepository @Inject constructor(
    @DistanceServiceScope private val distanceService: DistanceService
) {

    private val _distanceResultLoadingStatus = MutableLiveData<LoadingStatus>()
    val distanceResultLoadingStatus: LiveData<LoadingStatus> get() = _distanceResultLoadingStatus

    private val _distanceResults = MutableLiveData<DistanceResult>()
    val distanceResult: LiveData<DistanceResult> get() = _distanceResults

    suspend fun calculateDistances(data: DistanceData) {
        networkCall(_distanceResultLoadingStatus) {

            val startQuery = data.start.toString()
            val destinationQuery = data.destination.toString()

            val airlineDistance = getAirlineDistance(data)
            val drivingDistance = getDrivingDistance(startQuery, destinationQuery)
            val cyclingDistance = getCyclingDistance(startQuery, destinationQuery)
            val walkingDistance = getWalkingDistance(startQuery, destinationQuery)

            _distanceResults.postValue(
                DistanceResult(
                    airlineDistance,
                    drivingDistance,
                    cyclingDistance,
                    walkingDistance
                )
            )
        }
    }

    fun calculateOffline(data: DistanceData) {
        val airlineDistance = getAirlineDistance(data)
        _distanceResults.postValue(DistanceResult.onlyAirline(airlineDistance))
        _distanceResultLoadingStatus.postValue(LoadingStatus.Success)
    }

    private suspend fun getDrivingDistance(start: String, destination: String): DistanceModel {
        val drivingData =
            distanceService.getDrivingDistance(Keys.apiKey(), start, destination) as Map<*, *>
        return DistanceResultParser.parseDistance(drivingData)
    }

    private suspend fun getCyclingDistance(start: String, destination: String): DistanceModel {
        val cyclingData =
            distanceService.getCyclingDistance(Keys.apiKey(), start, destination) as Map<*, *>
        return DistanceResultParser.parseDistance(cyclingData)
    }

    private suspend fun getWalkingDistance(start: String, destination: String): DistanceModel {
        val walkingData =
            distanceService.getWalkingDistance(Keys.apiKey(), start, destination) as Map<*, *>
        return DistanceResultParser.parseDistance(walkingData)
    }

    private fun getAirlineDistance(data: DistanceData): Double {
        return AirlineDistanceCalculator.getDistance(data)
    }

    fun resetState() {
        _distanceResults.value = null
        _distanceResultLoadingStatus.value = null
    }

}