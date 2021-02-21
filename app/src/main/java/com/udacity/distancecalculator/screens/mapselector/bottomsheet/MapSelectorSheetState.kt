package com.udacity.distancecalculator.screens.mapselector.bottomsheet

enum class MapSelectorSheetState {
    EMPTY, SINGLE_SELECTION, FULL;

    companion object {
        private const val SCROLLER_HEIGHT = 5
        private const val SINGLE_HEIGHT = 280
        private const val FULL_HEIGHT = 720
    }

    fun getHeight(): Int {
        return when (this) {
            EMPTY -> SCROLLER_HEIGHT
            SINGLE_SELECTION -> SINGLE_HEIGHT
            FULL -> FULL_HEIGHT
        }
    }
}