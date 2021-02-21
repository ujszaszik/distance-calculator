package com.udacity.distancecalculator.screens.locationlist

import androidx.recyclerview.widget.DiffUtil
import com.udacity.distancecalculator.cities.model.CityModel

class LocationListItemCallback : DiffUtil.ItemCallback<CityModel>() {
    override fun areItemsTheSame(oldItem: CityModel, newItem: CityModel): Boolean {
        return oldItem.isEqualTo(newItem)
    }

    override fun areContentsTheSame(oldItem: CityModel, newItem: CityModel): Boolean {
        return oldItem == newItem
    }
}