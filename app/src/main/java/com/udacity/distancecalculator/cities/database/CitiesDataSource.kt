package com.udacity.distancecalculator.cities.database

import io.objectbox.Box
import javax.inject.Inject

class CitiesDataSource @Inject constructor(
    private val citiesBox: Box<CityEntity>
) {

    suspend fun getAllCities(): List<CityEntity> {
        return citiesBox.query().order(CityEntity_.name).build().find()
    }

    fun insertCities(cities: List<CityEntity>) = citiesBox.put(cities)

    fun getEntrySize(): Int = citiesBox.all.size

    fun deleteAllEntries() = citiesBox.removeAll()
}