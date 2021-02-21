package com.udacity.distancecalculator.screens.mapselector.bottomsheet

import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior

class BottomSheetSlideCallback(private val onSlide: (Float) -> Unit) :
    BottomSheetBehavior.BottomSheetCallback() {

    override fun onSlide(bottomSheet: View, slideOffset: Float) {
        onSlide.invoke(slideOffset)
    }

    override fun onStateChanged(bottomSheet: View, newState: Int) {}
}