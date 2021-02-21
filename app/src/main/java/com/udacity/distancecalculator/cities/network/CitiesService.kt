package com.udacity.distancecalculator.cities.network

import com.udacity.distancecalculator.cities.model.CityModel
import retrofit2.http.GET

interface CitiesService {

    @GET(".json")
    suspend fun getCountries(): List<CityModel>

}