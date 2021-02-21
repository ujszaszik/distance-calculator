package com.udacity.distancecalculator.screens.selector.form

class FormValidator(
    private val formResult: FormResult
) {

    private lateinit var doOnValid: () -> Unit
    private lateinit var doOnInvalid: () -> Unit
    private lateinit var doAfter: () -> Unit

    companion object {
        fun onResult(formResult: FormResult): FormValidator {
            return FormValidator(
                formResult
            )
        }
    }

    fun ifValid(doOnValid: () -> Unit): FormValidator {
        this.doOnValid = doOnValid
        return this
    }

    fun ifInvalid(doOnInvalid: () -> Unit): FormValidator {
        this.doOnInvalid = doOnInvalid
        return this
    }

    fun finally(doAfter: () -> Unit): FormValidator {
        this.doAfter = doAfter
        return this
    }

    fun validate() {
        when (formResult) {
            is FormResult.Complete -> doOnValid.invoke()
            is FormResult.Incomplete -> doOnInvalid.invoke()
        }
        doAfter.invoke()
    }
}
