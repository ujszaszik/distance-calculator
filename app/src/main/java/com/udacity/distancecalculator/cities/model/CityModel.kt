package com.udacity.distancecalculator.cities.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.udacity.distancecalculator.cities.database.CityEntity
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class CityModel(
    @Json(name = "city") var city: String,
    @Json(name = "country") var country: String,
    @Json(name = "lat") var latitude: Double,
    @Json(name = "lng") var longitude: Double,
    @Transient var type: LocationModelType = LocationModelType.SELECTED
) : Parcelable {

    fun toEntity(): CityEntity {
        return CityEntity(0, city, country, latitude, longitude)
    }

    override fun toString(): String = city

    fun isEmpty(): Boolean = city.isEmpty() || latitude == 0.0 || longitude == 0.0

    fun isProvided(): Boolean = type == LocationModelType.PROVIDED

    fun containsName(text: String): Boolean {
        return city.toLowerCase(Locale.getDefault()).contains(text)
    }

    fun isEqualTo(other: CityModel): Boolean {
        return latitude == other.latitude && longitude == other.longitude
    }
}

fun List<CityModel>.toEntities(): List<CityEntity> = map { it.toEntity() }

@Parcelize
enum class LocationModelType : Parcelable {
    SELECTED, PROVIDED
}