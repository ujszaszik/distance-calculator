package com.udacity.distancecalculator.common.collections

inline fun <T> Array<out T>.firstOr(default: T, predicate: (T) -> Boolean): T {
    for (element in this) if (predicate(element)) return element
    return default
}

fun <T> List<T>.second(): T = this[1]