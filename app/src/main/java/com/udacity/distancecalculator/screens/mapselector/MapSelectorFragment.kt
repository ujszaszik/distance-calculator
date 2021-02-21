package com.udacity.distancecalculator.screens.mapselector

import android.content.SharedPreferences
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.PointOfInterest
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.udacity.distancecalculator.R
import com.udacity.distancecalculator.common.livedata.observeNotNull
import com.udacity.distancecalculator.databinding.FragmentMapSelectorBinding
import com.udacity.distancecalculator.language.PoiLocalizer
import com.udacity.distancecalculator.location.LocationProviderUtil
import com.udacity.distancecalculator.preferences.MapStylePreference
import com.udacity.distancecalculator.preferences.getStringById
import com.udacity.distancecalculator.screens.mapselector.bottomsheet.BottomSheetSlideCallback
import com.udacity.distancecalculator.screens.mapselector.bottomsheet.SelectionState
import com.udacity.distancecalculator.screens.mapselector.search.MapTextSearchObserver
import com.udacity.distancecalculator.screens.mapselector.search.SearchTextListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MapSelectorFragment : MapHolderFragment() {

    private lateinit var binding: FragmentMapSelectorBinding
    private val viewModel: MapSelectorViewModel by viewModels()
    private lateinit var bottomSheetBehaviour: BottomSheetBehavior<ConstraintLayout>

    private var startMarker: Marker? = null
    private var destinationMarker: Marker? = null

    private lateinit var currentSelectionState: SelectionState

    @Inject
    lateinit var geocoder: Geocoder

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var poiLocalizer: PoiLocalizer

    @Inject
    lateinit var locationProviderUtil: LocationProviderUtil

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapSelectorBinding.inflate(inflater)
        mapView = binding.mapView

        createMap(savedInstanceState)

        return binding.root
    }

    override fun onMapReadyCallback() {
        showBottomSheet()
        moveCameraToInitialLocation()
        initMapGestures()
        initMapType()
        addPoiListener()

        setUpIconClickListeners()
        setUpSearchListener()
        setUpButtonClickListener()

        observeStartingPointEvent()
        observeDestinationEvent()
        observeSelectionState()
        observeSelectedPositionsForZoomFit()
        observeSearch()
        observeVisibleHeightForAdjustingZoomControls()
        observeCalculationRequestState()
    }

    private fun initMapGestures() {
        with(map.uiSettings) {
            isZoomGesturesEnabled = true
            isZoomControlsEnabled = true
        }
    }

    private fun addPoiListener() {
        map.setOnPoiClickListener { poi -> viewModel.onPoiSelected(poi) }
    }

    private fun initMapType() {
        sharedPreferences.getStringById(requireContext(), R.string.map_style_preference_key).run {
            map.mapType = MapStylePreference.getByCode(requireContext(), this).type
        }
    }

    private fun setUpIconClickListeners() {
        binding.bottomSheet.setStartingPointIconListener { viewModel.removeStartSelection() }
        binding.bottomSheet.setDestinationIconListener { viewModel.removeDestinationSelection() }
    }

    private fun observeSelectionState() {
        viewModel.selectedState.observeNotNull(this) { selectionState ->
            currentSelectionState = selectionState
            when (selectionState) {
                SelectionState.NONE -> onNoneSelected()
                SelectionState.SINGLE -> onSingleSelected()
                SelectionState.FULL -> onBothSelected()
            }
            binding.bottomSheet.adjust(selectionState.toMapSelectorSheetState())
        }
    }


    private fun onNoneSelected() {
        bottomSheetBehaviour.state = BottomSheetBehavior.STATE_COLLAPSED
        binding.bottomSheet.setButtonVisibility(false)
    }

    private fun onSingleSelected() {
        bottomSheetBehaviour.state = BottomSheetBehavior.STATE_EXPANDED
        binding.bottomSheet.setButtonVisibility(false)
    }

    private fun onBothSelected() {
        bottomSheetBehaviour.state = BottomSheetBehavior.STATE_EXPANDED
        binding.bottomSheet.setButtonVisibility(true)
    }

    private fun observeStartingPointEvent() {
        viewModel.startSelectionEvent.observeNotNull(this) { event ->
            when (event) {
                is MapSelectionEvent.Added -> onStartSelectionAdded(event.poi)
                is MapSelectionEvent.Removed -> onStartSelectionRemoved()
            }
        }
    }

    private fun onStartSelectionAdded(poi: PointOfInterest) {
        startMarker = map.addMarker(poi.toStartMarkerOptions(requireContext()))
        val localizedName = poiLocalizer.getLocalizedName(poi)
        binding.bottomSheet.startingPointToEnabledWithText(localizedName)
    }

    private fun onStartSelectionRemoved() {
        startMarker?.remove()
        binding.bottomSheet.startingPointToDisabled()
    }


    private fun observeDestinationEvent() {
        viewModel.destinationSelectionEvent.observeNotNull(this) { event ->
            when (event) {
                is MapSelectionEvent.Added -> onDestinationSelectionAdded(event.poi)
                is MapSelectionEvent.Removed -> onDestinationSelectionRemoved()
            }
        }
    }

    private fun onDestinationSelectionAdded(poi: PointOfInterest) {
        destinationMarker = map.addMarker(poi.toDestinationMarkerOptions(requireContext()))
        val localizedName = poiLocalizer.getLocalizedName(poi)
        binding.bottomSheet.destinationToEnabledWithText(localizedName)
    }

    private fun onDestinationSelectionRemoved() {
        destinationMarker?.remove()
        binding.bottomSheet.destinationToDisabled()
    }

    private fun observeSelectedPositionsForZoomFit() {
        viewModel.selectedPositions.observeNotNull(this) {
            map.fitZoomToMarkers(it!!)
        }
    }

    private fun moveCameraToInitialLocation() {
        map.moveCameraToDefault()
        locationProviderUtil
            .doOnSuccess { map.moveCameraTo(LatLng(it.latitude, it.longitude)) }
            .getCurrentLocation()
    }

    private fun showBottomSheet() {
        bottomSheetBehaviour = BottomSheetBehavior.from(binding.bottomSheet as ConstraintLayout)
        bottomSheetBehaviour.state = BottomSheetBehavior.STATE_COLLAPSED
        setUpBottomSheetBehaviorCallback()
    }

    private fun setUpSearchListener() {
        SearchTextListener(onSubmit = { viewModel.onSearch(it) }).run {
            binding.simpleSearchView.setOnQueryTextListener(this)
        }
    }

    private fun observeSearch() {
        val textSearchObserver =
            MapTextSearchObserver<String?>(geocoder, map, requireContext())
        viewModel.poiToSearch.observe(viewLifecycleOwner, textSearchObserver)
        binding.simpleSearchView.setQuery("", false)
        binding.simpleSearchView.clearFocus()
    }

    private fun observeVisibleHeightForAdjustingZoomControls() {
        binding.bottomSheet.visibleHeight.observeNotNull(this) {
            map.setPadding(0, 0, 0, it)
        }
    }

    private fun setUpButtonClickListener() {
        binding.bottomSheet.setButtonClickListener { viewModel.onCalculateRequest() }
    }

    private fun observeCalculationRequestState() {
        viewModel.calculationRequestState.observeNotNull(this) { state ->
            when (state) {
                is CalculationRequestState.Valid -> navigateToResultsPage(state)
                is CalculationRequestState.Incomplete -> showIncompleteToast()
            }
        }
    }

    private fun navigateToResultsPage(state: CalculationRequestState.Valid) {
        val (start, destination) = state
        MapSelectorFragmentDirections.actionMapSelectorFragmentToResultFragment(start, destination)
            .run { findNavController().navigate(this) }
            .run { viewModel.resetState() }
    }

    private fun showIncompleteToast() {
        Toast.makeText(context, "Please select starting point and destination!", Toast.LENGTH_SHORT)
            .show()
    }

    private fun setUpBottomSheetBehaviorCallback() {
        val doOnSlide: (Float) -> Unit = { it -> map.setPadding(0, 0, 0, it.toInt()) }
        bottomSheetBehaviour.addBottomSheetCallback(BottomSheetSlideCallback(doOnSlide))
    }

}