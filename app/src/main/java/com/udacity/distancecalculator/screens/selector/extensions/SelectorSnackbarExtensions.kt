package com.udacity.distancecalculator.screens.selector.extensions

import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.udacity.distancecalculator.R

fun showLocationDisabledSnackbar(view: View) {
    Snackbar.make(
        view,
        view.context.getString(R.string.label_snackbar_enable_location),
        Snackbar.LENGTH_SHORT
    ).show()
}

fun showNotSelectedFromListSnackbar(view: View) {
    Snackbar.make(
        view,
        view.context.getString(R.string.label_snackbar_empty_selection),
        Snackbar.LENGTH_SHORT
    ).show()
}

fun showEmptyDatabaseSnackbar(view: View) {
    Snackbar.make(
        view,
        view.context.getString(R.string.label_snackbar_enable_network),
        Snackbar.LENGTH_SHORT
    ).show()
}