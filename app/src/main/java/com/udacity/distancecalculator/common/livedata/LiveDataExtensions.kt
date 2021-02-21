package com.udacity.distancecalculator.common.livedata

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observeNotNull(fragment: Fragment, block: (T) -> Unit) {
    observe(fragment.viewLifecycleOwner, Observer {
        it?.let { block.invoke(it) }
    })
}

fun LiveData<Boolean>.observeIfTrue(fragment: Fragment, block: () -> Unit) {
    observe(fragment.viewLifecycleOwner, Observer {
        if (it != null && it) block.invoke()
    })
}

fun LiveData<Boolean>.observeIfTrue(activity: AppCompatActivity, block: () -> Unit) {
    observe(activity, Observer { if (it != null && it) block.invoke() })
}

fun MutableLiveData<Boolean>.postTrue() = postValue(true)

fun <T, O, R> LiveData<T>.combineWith(
    other: LiveData<O>,
    combineFunction: (T?, O?) -> R
): LiveData<R> {
    return MediatorLiveData<R>().also { result ->
        result.addSource(this) {
            result.value = combineFunction(this.value, other.value)
        }
        result.addSource(other) {
            result.value = combineFunction(this.value, other.value)
        }
    }
}