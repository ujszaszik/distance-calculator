package com.udacity.distancecalculator.distance.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DistanceModel(
    var distance: Double,
    var duration: Double,
    var state: DistanceResultState = DistanceResultState.SUCCESS
) : Parcelable {

    companion object {
        fun noData(): DistanceModel {
            return DistanceModel(0.0, 0.0, DistanceResultState.NO_DATA)
        }
    }
}

enum class DistanceResultState {
    SUCCESS, NO_DATA
}