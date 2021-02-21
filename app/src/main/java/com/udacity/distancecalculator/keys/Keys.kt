package com.udacity.distancecalculator.keys

private const val NATIVE_LIBRARY = "native-lib"

object Keys {

    init {
        System.loadLibrary(NATIVE_LIBRARY)
    }

    external fun apiKey(): String
}