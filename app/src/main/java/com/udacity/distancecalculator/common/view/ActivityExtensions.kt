package com.udacity.distancecalculator.common.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

fun <T : AppCompatActivity> AppCompatActivity.startActivity(activityClass: Class<T>) {
    Intent(this, activityClass).also { startActivity(it) }
}