package com.udacity.distancecalculator.screens.result.animation

import android.view.Gravity
import android.view.View
import android.view.animation.OvershootInterpolator
import com.robinhood.ticker.TickerUtils
import com.robinhood.ticker.TickerView
import com.udacity.distancecalculator.language.LocaleUtil

object TickerViewInitializer {

    fun init(tickerView: TickerView) {
        with(tickerView) {
            setCharacterLists(TickerUtils.provideNumberList())
            animationDuration = 2000
            animationInterpolator = OvershootInterpolator()
            gravity = getGravityByLocale(this)
            setPreferredScrollingDirection(TickerView.ScrollingDirection.ANY)
        }
    }

    private fun getGravityByLocale(tickerView: TickerView): Int {
        return if (LocaleUtil.isRtl(tickerView.context)) Gravity.END
        else Gravity.START
    }
}