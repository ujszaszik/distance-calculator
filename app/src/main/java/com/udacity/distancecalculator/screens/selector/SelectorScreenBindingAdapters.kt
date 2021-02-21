package com.udacity.distancecalculator.screens.selector

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.udacity.distancecalculator.R
import java.util.*

class FlagLoader(private val context: Context) {

    companion object {
        private const val FLAGS_BASE_URL = "https://www.countryflags.io/"
        private const val FLAGS_URL_POSTFIX = "/shiny/64.png"
    }

    fun loadInto(countryCode: String, imageView: ImageView) {
        val url = FLAGS_BASE_URL + countryCode.toLowerCase(Locale.ROOT) + FLAGS_URL_POSTFIX
        Glide.with(context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.loading_img)
            .error(R.drawable.flag_world)
            .into(imageView)
    }
}