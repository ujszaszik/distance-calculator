package com.udacity.distancecalculator.screens.mapselector.search

import android.widget.SearchView

class SearchTextListener(
    private val onSubmit: (String) -> Unit,
    private val onEmpty: () -> Unit = {}
) : SearchView.OnQueryTextListener {
    override fun onQueryTextChange(newText: String): Boolean {
        if (newText.isEmpty()) onEmpty.invoke()
        return false
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        return onSubmit.invoke(query).run { false }
    }
}