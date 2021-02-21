package com.udacity.distancecalculator.screens.mapselector.search

import android.content.Context
import android.widget.Toast
import com.udacity.distancecalculator.R

fun Context.showNotFoundToast() {
    Toast.makeText(this, getString(R.string.label_map_search_not_found), Toast.LENGTH_SHORT).show()
}

fun Context.showEmptySearchToast() {
    Toast.makeText(this, getString(R.string.label_map_search_empty), Toast.LENGTH_SHORT).show()
}