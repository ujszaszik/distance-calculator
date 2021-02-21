package com.udacity.distancecalculator.cities.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(ApplicationComponent::class)
class CitiesNetworkModule {

    companion object {
        private const val COUNTRIES_API_BASE_URL =
            "https://distance-caulculator-default-rtdb.europe-west1.firebasedatabase.app/"
    }

    @Provides
    @CitiesRetrofit
    fun provideCountriesRetrofit(
        converterFactory: MoshiConverterFactory,
        httpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(COUNTRIES_API_BASE_URL)
            .addConverterFactory(converterFactory)
            .client(httpClient)
            .build()
    }

    @Provides
    @CitiesServiceScope
    fun provideCountriesService(@CitiesRetrofit retrofit: Retrofit): CitiesService =
        retrofit.create(CitiesService::class.java)
}