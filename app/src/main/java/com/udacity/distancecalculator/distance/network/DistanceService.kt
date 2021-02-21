package com.udacity.distancecalculator.distance.network

import retrofit2.http.GET
import retrofit2.http.Query

interface DistanceService {

    @GET("driving-car")
    suspend fun getDrivingDistance(
        @Query("api_key") apiKey: String,
        @Query("start") start: String,
        @Query("end") end: String
    ): Any

    @GET("cycling-regular")
    suspend fun getCyclingDistance(
        @Query("api_key") apiKey: String,
        @Query("start") start: String,
        @Query("end") end: String
    ): Any

    @GET("foot-walking")
    suspend fun getWalkingDistance(
        @Query("api_key") apiKey: String,
        @Query("start") start: String,
        @Query("end") end: String
    ): Any

}