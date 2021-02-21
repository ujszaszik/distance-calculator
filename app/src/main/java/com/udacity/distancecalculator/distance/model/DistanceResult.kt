package com.udacity.distancecalculator.distance.model

data class DistanceResult(
    val distanceInAirline: Double,
    val roadDistance: DistanceModel,
    val cyclingDistance: DistanceModel,
    val walkDistance: DistanceModel
) {

    companion object {
        fun onlyAirline(distanceInAirline: Double): DistanceResult {
            return DistanceResult(
                distanceInAirline,
                DistanceModel.noData(),
                DistanceModel.noData(),
                DistanceModel.noData()
            )
        }
    }
}