package com.udacity.distancecalculator.screens.mapselector.bottomsheet

enum class SelectionState {
    NONE, SINGLE, FULL;

    companion object {
        fun <T : Any> fromValues(first: T?, second: T?): SelectionState {
            return when {
                areBothPresent(first, second) -> FULL
                isOnlyOnePresent(first, second) -> SINGLE
                else -> NONE
            }
        }

    }

    fun toMapSelectorSheetState(): MapSelectorSheetState {
        return when (this) {
            NONE -> MapSelectorSheetState.EMPTY
            SINGLE -> MapSelectorSheetState.SINGLE_SELECTION
            FULL -> MapSelectorSheetState.FULL
        }
    }

}

fun <T : Any> areBothPresent(first: T?, second: T?): Boolean {
    return first != null && second != null
}

fun <T : Any> isOnlyOnePresent(first: T?, second: T?): Boolean {
    return first != null && second == null
            || first == null && second != null
}