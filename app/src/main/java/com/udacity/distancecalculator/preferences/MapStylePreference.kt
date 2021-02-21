package com.udacity.distancecalculator.preferences

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.udacity.distancecalculator.R
import com.udacity.distancecalculator.common.collections.firstOr

enum class MapStylePreference(val keyId: Int, val type: Int) {
    DEFAULT(R.string.map_style_default_key, GoogleMap.MAP_TYPE_NORMAL),
    TERRAIN(R.string.map_style_terrain_key, GoogleMap.MAP_TYPE_TERRAIN),
    HYBRID(R.string.map_style_hybrid_key, GoogleMap.MAP_TYPE_HYBRID),
    SATELLITE(R.string.map_style_satellite_key, GoogleMap.MAP_TYPE_SATELLITE);

    companion object {

        fun getByCode(context: Context, code: String): MapStylePreference {
            return values().firstOr(DEFAULT) { code == context.getString(it.keyId) }
        }
    }
}