package com.udacity.distancecalculator.screens.selector.animation

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.RippleDrawable
import android.widget.Button
import com.udacity.distancecalculator.R
import com.udacity.distancecalculator.common.animation.fadeOutOf
import com.udacity.distancecalculator.common.view.getColor

class ButtonAnimator() {

    private lateinit var context: Context
    private lateinit var button: Button
    private lateinit var buttons: List<Button>
    private lateinit var onFinished: () -> Unit

    companion object {
        private const val DEFAULT_DURATION = 300L
    }


    fun on(button: Button): ButtonAnimator {
        this.button = button
        this.context = button.context
        return this
    }

    fun withButtons(buttons: List<Button>): ButtonAnimator {
        this.buttons = buttons
        return this
    }

    fun onFinished(onFinished: () -> Unit): ButtonAnimator {
        this.onFinished = onFinished
        return this
    }

    fun start() {
        buttons.forEach { current ->
            if (current.id == button.id)
                changeBackgroundColor(current, context.getColor(R.color.purple_700))
            else changeBackgroundColor(current, Color.GRAY).run { fadeOut(current) }
        }
    }

    private fun changeBackgroundColor(button: Button, changeTo: Int) {
        val buttonBackground = button.background as RippleDrawable
        val currentBgColor = buttonBackground.getColor()
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), currentBgColor, changeTo)
        colorAnimation.duration = DEFAULT_DURATION
        colorAnimation.addUpdateListener { animator -> button.setBackgroundColor(animator.animatedValue as Int) }
        colorAnimation.addListener(AnimationFinishedListener(onFinished))
        colorAnimation.start()
    }

    private fun fadeOut(button: Button) {
        val fadeAnimator = ObjectAnimator().fadeOutOf(button)
        fadeAnimator.duration = DEFAULT_DURATION
        fadeAnimator.start()
    }
}