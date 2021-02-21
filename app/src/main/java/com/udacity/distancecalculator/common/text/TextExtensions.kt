package com.udacity.distancecalculator.common.text

import com.udacity.distancecalculator.distance.model.LocationPoint
import java.util.*

fun String.splitBySpaces(): List<String> = this.split(" ")

fun String.replaceLineBreak(): String = replace("\n", " ")

fun String.containsLowercase(text: String): Boolean =
    this.toLowerCase(Locale.ROOT).contains(text.toLowerCase())

fun String?.firstLetterOrEmpty() = this?.let { substring(0, 1) } ?: ""

fun StringBuilder.appendColon(): StringBuilder = append(",")

fun StringBuilder.appendSemiColon(): StringBuilder = append(";")

fun StringBuilder.appendDouble(double: Double): StringBuilder = append(double.toString())

fun StringBuilder.appendLocationPoint(locationPoint: LocationPoint): StringBuilder =
    append(locationPoint.toString())
