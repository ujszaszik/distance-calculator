package com.udacity.distancecalculator.cities.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.distancecalculator.R
import com.udacity.distancecalculator.cities.database.CitiesDataSource
import com.udacity.distancecalculator.cities.model.CityModel
import com.udacity.distancecalculator.cities.model.toEntities
import com.udacity.distancecalculator.cities.network.CitiesService
import com.udacity.distancecalculator.cities.network.CitiesServiceScope
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CitiesRepository @Inject constructor(
    @CitiesServiceScope private val citiesService: CitiesService,
    private val localDataSource: CitiesDataSource,
    @ApplicationContext private val context: Context
) {

    private val _citiesList = MutableLiveData<List<CityModel>>()
    val citiesList: LiveData<List<CityModel>> get() = _citiesList

    private suspend fun getRemoteCities(): List<CityModel> {
        return citiesService.getCountries()
    }

    suspend fun fetchCountriesList() {
        localDataSource.getAllCities().map { it.toModel() }
            .run { _citiesList.postValue(this) }
    }

    suspend fun cacheRemoteCities() {
        if (!isAlreadyFetched()) {
            val remoteCities = getRemoteCities()
            clearCache()
            localDataSource.insertCities(remoteCities.toEntities())
        }
    }

    fun isAlreadyFetched(): Boolean {
        return context.resources.getInteger(R.integer.cities_list_size) == localDataSource.getEntrySize()
    }

    private fun clearCache() {
        localDataSource.deleteAllEntries()
    }
}