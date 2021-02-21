package com.udacity.distancecalculator.common.network

sealed class LoadingStatus {

    object Loading : LoadingStatus()

    object Success : LoadingStatus()

    class Error(private val error: Throwable) : LoadingStatus() {
        fun getErrorMessage(): String? = error.message
    }

    object NoResults : LoadingStatus()

    object Disconnected : LoadingStatus()
}