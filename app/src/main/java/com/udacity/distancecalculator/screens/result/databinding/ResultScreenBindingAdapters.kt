package com.udacity.distancecalculator.screens.result.databinding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.robinhood.ticker.TickerView

@BindingAdapter("app:distance")
fun TickerView.distance(distanceInMeters: Double) {
    text = DistanceToTextConverter(context).getConvertedString(distanceInMeters)
}

@BindingAdapter("app:timeText")
fun TextView.timeText(value: Double) {
    text = TimeToTextConverter.convertToString(value)
}