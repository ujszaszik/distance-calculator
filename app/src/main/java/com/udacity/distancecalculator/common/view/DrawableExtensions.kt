package com.udacity.distancecalculator.common.view

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

private const val COLOR_FIELD_NAME = "mColor"

fun RippleDrawable.getColor(): Int {
    val state = constantState as Drawable.ConstantState
    val fields = state.javaClass.kotlin.memberProperties
    fields.forEach {
        if (it.name == COLOR_FIELD_NAME) {
            it.isAccessible = true
            val colorStateList = it.getter.call(state) as ColorStateList
            return colorStateList.defaultColor
        }
    }
    return 0
}