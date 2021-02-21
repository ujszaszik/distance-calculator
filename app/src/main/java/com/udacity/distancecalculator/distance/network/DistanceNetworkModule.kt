package com.udacity.distancecalculator.distance.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(ApplicationComponent::class)
class DistanceNetworkModule {

    companion object {
        private const val OPEN_ROUTE_SERVICE_BASE_URL =
            "https://api.openrouteservice.org/v2/directions/"
    }

    @Provides
    @DistanceRetrofit
    fun provideRetrofit(
        converterFactory: MoshiConverterFactory,
        httpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(OPEN_ROUTE_SERVICE_BASE_URL)
            .addConverterFactory(converterFactory)
            .client(httpClient)
            .build()
    }

    @Provides
    @DistanceServiceScope
    fun provideDistanceService(@DistanceRetrofit retrofit: Retrofit): DistanceService =
        retrofit.create(DistanceService::class.java)
}