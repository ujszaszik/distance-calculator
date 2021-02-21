package com.udacity.distancecalculator

import android.app.Application
import android.os.Build
import com.udacity.distancecalculator.common.network.NetworkUtil
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var networkUtil: NetworkUtil

    companion object {
        private val currentVersion = Build.VERSION.SDK_INT
        private const val qVersion = Build.VERSION_CODES.Q
        private const val nVersion = Build.VERSION_CODES.N

        fun isRunningOnAtLeastQ(): Boolean = currentVersion >= qVersion

        fun isRunningOnAtLeastN(): Boolean = currentVersion >= nVersion
    }

    override fun onCreate() {
        super.onCreate()
        networkUtil.registerNetworkCallback()
    }

}