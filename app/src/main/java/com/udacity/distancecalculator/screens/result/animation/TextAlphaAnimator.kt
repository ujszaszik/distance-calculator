package com.udacity.distancecalculator.screens.result.animation

import android.view.animation.AlphaAnimation
import android.widget.TextView


object TextAlphaAnimator {

    private const val DEFAULT_DURATION = 2000L
    private const val TRANSPARENT = 0.0f
    private const val OPAQUE = 1.0f

    fun startAnimationOn(textView: TextView) {
        val animation1 = AlphaAnimation(TRANSPARENT, OPAQUE)
        animation1.duration = DEFAULT_DURATION
        animation1.fillAfter = true
        textView.startAnimation(animation1)
    }
}