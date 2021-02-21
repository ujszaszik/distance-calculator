package com.udacity.distancecalculator.location

import com.udacity.distancecalculator.App

object LocationRequestCode {

    private const val resultCodeForBackgroundAndForeground = 33
    private const val resultCodeForOnlyForeground = 34

    fun forCurrentApiLevel(): Int {
        return when {
            App.isRunningOnAtLeastQ() -> resultCodeForBackgroundAndForeground
            else -> resultCodeForOnlyForeground
        }
    }
}