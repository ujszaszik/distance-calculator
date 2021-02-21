package com.udacity.distancecalculator.common.view

import android.content.res.ColorStateList
import android.graphics.Rect
import android.text.Editable
import android.text.SpannableStringBuilder
import android.view.View
import android.view.View.GONE
import android.widget.TextView
import androidx.core.view.ViewCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

fun allGone(vararg views: View) = views.forEach { it.visibility = GONE }

fun TextView.clear() {
    this.text = "".toEditable()
}

fun clearAll(vararg textViews: TextView) = textViews.forEach { it.clear() }

fun Any.toEditable(): Editable {
    return SpannableStringBuilder(this.toString())
}

fun View.getVisibleHeight(): Int {
    return Rect().run {
        getGlobalVisibleRect(this)
        height()
    }
}

fun FloatingActionButton.setBgColor(colorId: Int) {
    ViewCompat.setBackgroundTintList(this, ColorStateList.valueOf(colorId))
}

fun FloatingActionButton.getBgColor(): Int {
    return backgroundTintList?.defaultColor ?: 0
}