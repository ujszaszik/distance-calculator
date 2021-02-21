package com.udacity.distancecalculator.common.network

enum class HttpStatusCode(private val code: Int) {
    OK(200),
    BAD_REQUEST(400),
    NOT_FOUND(404),
    INTERNAL(500);

    companion object {
        fun byCodeString(codeString: String?): HttpStatusCode? {
            return values().firstOrNull { it.code == codeString?.toInt() }
        }
    }

    fun isRequestError() = this == BAD_REQUEST || this == NOT_FOUND
}