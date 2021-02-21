package com.udacity.distancecalculator.screens.locationlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udacity.distancecalculator.cities.model.CityModel
import com.udacity.distancecalculator.databinding.RecyclerRowBinding

class LocationListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        private lateinit var binding: RecyclerRowBinding

        fun from(parent: ViewGroup): LocationListViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            binding = RecyclerRowBinding.inflate(inflater, parent, false)
            return LocationListViewHolder(binding.root)
        }
    }

    fun bind(city: CityModel) {
        binding.city = city
        binding.executePendingBindings()
    }
}