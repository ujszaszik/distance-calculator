package com.udacity.distancecalculator.screens.locationlist

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.udacity.distancecalculator.screens.selector.FlagLoader

@BindingAdapter("app:flag")
fun ImageView.flag(country: String) {
    FlagLoader(context).loadInto(country, this)
}