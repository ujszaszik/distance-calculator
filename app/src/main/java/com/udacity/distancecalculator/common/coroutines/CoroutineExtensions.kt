package com.udacity.distancecalculator.common.coroutines

import androidx.lifecycle.MutableLiveData
import com.udacity.distancecalculator.common.network.LoadingStatus
import com.udacity.distancecalculator.common.network.NetworkUtil
import com.udacity.distancecalculator.common.network.isWrongRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> IO(block: suspend () -> T): T {
    return withContext(Dispatchers.IO) {
        block.invoke()
    }
}

suspend fun <T> networkCall(
    liveData: MutableLiveData<LoadingStatus>,
    block: suspend () -> T
): T? {
    var result: T? = null
    if (NetworkUtil.isConnected) {
        liveData.postValue(LoadingStatus.Loading)
        try {
            result = IO { block.invoke() }
            liveData.postValue(LoadingStatus.Success)
        } catch (t: Throwable) {
            if (isWrongRequest(t)) liveData.postValue(LoadingStatus.NoResults)
            else liveData.postValue(LoadingStatus.Error(t))
        }
    } else {
        liveData.postValue(LoadingStatus.Disconnected)
    }
    return result
}