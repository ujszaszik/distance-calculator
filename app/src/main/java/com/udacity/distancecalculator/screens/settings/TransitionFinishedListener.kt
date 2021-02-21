package com.udacity.distancecalculator.screens.settings

import androidx.constraintlayout.motion.widget.MotionLayout

class TransitionFinishedListener(private val block: () -> Unit): MotionLayout.TransitionListener {

    override fun onTransitionTrigger(motionLayout: MotionLayout?, triggerId: Int, positive: Boolean, progress: Float) {}

    override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) {}

    override fun onTransitionChange(motionLayout: MotionLayout?, startId: Int, endId: Int, progress: Float) {}

    override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
        block.invoke()
    }
}