package com.udacity.distancecalculator.screens.result.databinding

import org.junit.Test

class TimeToTextConverterTest {

    @Test
    fun test() {
        val time: Double = 7741.6
        println(TimeToTextConverter.convertToString(time))
    }

}