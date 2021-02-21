package com.udacity.distancecalculator.screens.selector.animation

import android.animation.Animator

class AnimationFinishedListener(private val onFinished: () -> Unit) : Animator.AnimatorListener {
    override fun onAnimationRepeat(animator: Animator) {}

    override fun onAnimationEnd(animator: Animator) = onFinished.invoke()

    override fun onAnimationCancel(animator: Animator) {}

    override fun onAnimationStart(animator: Animator) {}
}