package com.udacity.distancecalculator.common.animation

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.udacity.distancecalculator.common.view.getBgColor

private const val OPAQUE = 1f
private const val TRANSPARENT = 0f

fun ObjectAnimator.fadeOutOf(view: View): ObjectAnimator =
    ObjectAnimator.ofFloat(
        view, View.ALPHA,
        OPAQUE,
        TRANSPARENT
    )

private const val PROPERTY_BACKGROUND_TINT = "backgroundTint"

@SuppressLint("ObjectAnimatorBinding")
fun ObjectAnimator.changeBackgroundOfFab(
    button: FloatingActionButton,
    changeTo: Int
): ObjectAnimator {
    return ObjectAnimator.ofInt(button, PROPERTY_BACKGROUND_TINT, button.getBgColor(), changeTo)
        .apply { setEvaluator(ArgbEvaluator()) }
}

private const val ROTATION_DEGREE = 360f

fun oneRoundRotation(view: View): ViewPropertyAnimatorCompat {
    return ViewCompat.animate(view).rotation(ROTATION_DEGREE)
}