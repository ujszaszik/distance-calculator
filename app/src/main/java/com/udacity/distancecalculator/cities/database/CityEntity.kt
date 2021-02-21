package com.udacity.distancecalculator.cities.database

import com.udacity.distancecalculator.cities.model.CityModel
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class CityEntity(
    @Id var id: Long = 0,
    var name: String,
    var country: String,
    var latitude: Double,
    var longitude: Double
) {

    fun toModel(): CityModel = CityModel(name, country, latitude, longitude)
}