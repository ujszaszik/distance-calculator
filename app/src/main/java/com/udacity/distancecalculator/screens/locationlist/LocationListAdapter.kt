package com.udacity.distancecalculator.screens.locationlist

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.udacity.distancecalculator.cities.model.CityModel

class LocationListAdapter :
    ListAdapter<CityModel, LocationListViewHolder>(LocationListItemCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationListViewHolder {
        return LocationListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: LocationListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}