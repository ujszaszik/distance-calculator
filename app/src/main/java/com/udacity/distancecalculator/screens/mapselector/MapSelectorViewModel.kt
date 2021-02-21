package com.udacity.distancecalculator.screens.mapselector

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PointOfInterest
import com.udacity.distancecalculator.common.livedata.combineWith
import com.udacity.distancecalculator.screens.mapselector.bottomsheet.SelectionState
import com.udacity.distancecalculator.screens.mapselector.bottomsheet.areBothPresent

class MapSelectorViewModel @ViewModelInject constructor() : ViewModel() {

    private var startSelection: PointOfInterest? = null
    private val _startSelectionEvent = MutableLiveData<MapSelectionEvent>()
    val startSelectionEvent: LiveData<MapSelectionEvent> get() = _startSelectionEvent

    private var destinationSelection: PointOfInterest? = null
    private val _destinationSelectionEvent = MutableLiveData<MapSelectionEvent>()
    val destinationSelectionEvent: LiveData<MapSelectionEvent> get() = _destinationSelectionEvent

    val selectedState: LiveData<SelectionState> =
        startSelectionEvent.combineWith(destinationSelectionEvent) { _, _ ->
            SelectionState.fromValues(startSelection, destinationSelection)
        }

    val selectedPositions: LiveData<List<LatLng>?> =
        startSelectionEvent.combineWith(destinationSelectionEvent) { _, _ ->
            if (areBothPresent(startSelection, destinationSelection)) {
                listOf(startSelection!!.latLng, destinationSelection!!.latLng)
            } else null
        }

    private val _poiToSearch = MutableLiveData<String?>()
    val poiToSearch: LiveData<String?> get() = _poiToSearch

    private val _calculationRequestState = MutableLiveData<CalculationRequestState>()
    val calculationRequestState: LiveData<CalculationRequestState> get() = _calculationRequestState

    fun onPoiSelected(poi: PointOfInterest) {
        if (!isStartingPointSelected()) addStartSelection(poi)
        else if (!isDestinationSelected()) addDestinationSelection(poi)
    }

    private fun isStartingPointSelected() = startSelection != null

    private fun isDestinationSelected() = destinationSelection != null

    private fun addStartSelection(poi: PointOfInterest) {
        this.startSelection = poi
        _startSelectionEvent.postValue(MapSelectionEvent.Added(poi))
    }

    internal fun removeStartSelection() {
        this.startSelection = null
        _startSelectionEvent.postValue(MapSelectionEvent.Removed)
    }

    private fun addDestinationSelection(poi: PointOfInterest) {
        this.destinationSelection = poi
        _destinationSelectionEvent.postValue(MapSelectionEvent.Added(poi))
    }

    internal fun removeDestinationSelection() {
        this.destinationSelection = null
        _destinationSelectionEvent.postValue(MapSelectionEvent.Removed)
    }

    fun onSearch(text: String?) {
        _poiToSearch.postValue(text)
    }

    fun onCalculateRequest() {
        var requestState: CalculationRequestState = CalculationRequestState.Incomplete
        startSelection?.toCityModel()?.let { start ->
            destinationSelection?.toCityModel()?.let { destination ->
                requestState = CalculationRequestState.Valid(start, destination)
            }
        }
        _calculationRequestState.postValue(requestState)
    }

    fun resetState() {
        _calculationRequestState.value = null
    }


}