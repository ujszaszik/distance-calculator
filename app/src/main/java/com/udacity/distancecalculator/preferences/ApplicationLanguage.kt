package com.udacity.distancecalculator.preferences

import com.udacity.distancecalculator.common.collections.firstOr

enum class ApplicationLanguage(val text: String, val localeCode: String) {
    ENGLISH("english", "en"),
    HEBREW("hebrew", "iw"),
    ARABIC("arabic", "ar"),
    HINDI("hindi", "hi");

    companion object {
        fun getLocalCode(text: String): String {
            return getLanguage(text).localeCode
        }

        fun getLanguage(text: String): ApplicationLanguage {
            return values().firstOr(ENGLISH) { text == it.text }
        }

        fun isRightToLeft(countryCode: String): Boolean {
            return getLanguageByLocaleCode(countryCode).run {
                this == HEBREW || this == ARABIC
            }
        }

        private fun getLanguageByLocaleCode(localeCode: String): ApplicationLanguage {
            return values().firstOr(ENGLISH) { localeCode == it.localeCode }
        }

    }
}