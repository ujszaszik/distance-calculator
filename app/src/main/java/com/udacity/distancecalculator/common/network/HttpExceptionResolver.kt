package com.udacity.distancecalculator.common.network

import com.udacity.distancecalculator.common.collections.second
import com.udacity.distancecalculator.common.text.splitBySpaces
import retrofit2.HttpException

fun isWrongRequest(t: Throwable) =
    t is HttpException && t.errorStatus()?.isRequestError() ?: false

private fun Throwable.errorStatus(): HttpStatusCode? {
    val statusCodeSplitString: List<String>? = message?.splitBySpaces()
    if (statusCodeSplitString?.size != 3) return null
    val statusCode: String? = statusCodeSplitString.second()
    return HttpStatusCode.byCodeString(statusCode)
}