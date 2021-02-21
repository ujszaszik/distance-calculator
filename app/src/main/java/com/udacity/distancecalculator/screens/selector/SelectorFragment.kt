package com.udacity.distancecalculator.screens.selector

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.udacity.distancecalculator.R
import com.udacity.distancecalculator.cities.model.CityModel
import com.udacity.distancecalculator.common.livedata.observeIfTrue
import com.udacity.distancecalculator.common.livedata.observeNotNull
import com.udacity.distancecalculator.common.network.NetworkUtil
import com.udacity.distancecalculator.common.view.clearAll
import com.udacity.distancecalculator.common.view.startActivity
import com.udacity.distancecalculator.common.view.toEditable
import com.udacity.distancecalculator.databinding.FragmentSelectorBinding
import com.udacity.distancecalculator.location.LocationPermissionUtil
import com.udacity.distancecalculator.location.LocationProviderUtil
import com.udacity.distancecalculator.location.LocationSettingsUtil
import com.udacity.distancecalculator.screens.selector.animation.ButtonAnimator
import com.udacity.distancecalculator.screens.selector.cities.CitiesAdapter
import com.udacity.distancecalculator.screens.selector.cities.CitiesAdapterListener
import com.udacity.distancecalculator.screens.selector.cities.CityValidator
import com.udacity.distancecalculator.screens.selector.extensions.*
import com.udacity.distancecalculator.screens.selector.form.FormResult
import com.udacity.distancecalculator.screens.selector.form.FormValidator
import com.udacity.distancecalculator.screens.settings.SettingsActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SelectorFragment : Fragment() {

    private lateinit var binding: FragmentSelectorBinding
    private val viewModel: SelectorViewModel by viewModels()

    private val startCityListener = CitiesAdapterListener()
    private val destinationCityListener = CitiesAdapterListener()

    @Inject
    lateinit var locationPermissionUtil: LocationPermissionUtil

    @Inject
    lateinit var locationSettingsUtil: LocationSettingsUtil

    @Inject
    lateinit var locationProviderUtil: LocationProviderUtil

    private lateinit var buttons: List<Button>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSelectorBinding.inflate(inflater)

        buttons = listOf(binding.calculateButton, binding.openMapButton, binding.settingsButton)

        setButtonListeners()
        setLocationListViewListener()

        initStartLocationTextLayout()
        handleLocationPermission()

        observeCities()
        observeIfDisconnectedAtViewCreation()
        observeConnectionChange()
        observeFormValidation()
        observeLocationPermissionResult()
        observeDisabledLocation()
        observeMapRequest()
        observeSettingsRequest()

        return binding.root
    }

    private fun setFieldsClear() =
        clearAll(binding.startLocationTextView, binding.destinationLocationTextView)

    private fun setLocationListViewListener() {
        binding.listAllCitiesView.setOnClickListener { navigateToCitiesList() }
    }

    private fun navigateToCitiesList() {
        SelectorFragmentDirections.actionSelectorFragmentToLocationListFragment().run {
            findNavController().navigate(this)
        }
    }

    private fun setButtonListeners() {
        setCalculateButtonListener()
        setOpenMapButtonListener()
        setGoToSettingsButtonListener()
    }

    private fun setCalculateButtonListener() {
        binding.calculateButton.setOnClickListener {
            binding.startLocationTextView.clearFocus()
            binding.destinationLocationTextView.clearFocus()
            val startCity = startCityListener.currentSelection
            val destinationCity = destinationCityListener.currentSelection
            viewModel.validateFields(startCity, destinationCity)
        }
    }

    private fun setOpenMapButtonListener() {
        with(binding.openMapButton) {
            setOnClickListener {
                ButtonAnimator().on(this)
                    .withButtons(buttons)
                    .onFinished { viewModel.onOpenMapRequested() }
                    .start()
            }
        }
    }

    private fun observeMapRequest() {
        viewModel.isOpenMapScreenRequested.observeIfTrue(this) {
            navigateToMapSelectorScreen()
            viewModel.resetMapRequest()
        }
    }

    private fun navigateToMapSelectorScreen() {
        SelectorFragmentDirections.actionSelectorFragmentToMapSelectorFragment().run {
            findNavController().navigate(this)
            setFieldsClear()
        }
    }


    private fun setGoToSettingsButtonListener() {
        with(binding.settingsButton) {
            setOnClickListener {
                ButtonAnimator().on(this)
                    .withButtons(buttons)
                    .onFinished { viewModel.onSettingsScreenRequest() }
                    .start()
            }
        }
    }

    private fun observeSettingsRequest() {
        viewModel.isSettingsPageRequested.observeIfTrue(this) {
            navigateToSettingsScreen()
            viewModel.resetSettingsRequest()
        }
    }

    private fun navigateToSettingsScreen() {
        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.startActivity(SettingsActivity::class.java)
    }


    private fun observeLocationPermissionResult() {
        locationPermissionUtil.isGranted.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) locationSettingsUtil.checkLocationSettings()
                else doOnLocationNotGranted()
                locationPermissionUtil.resetGrantState()
            }
        })
    }

    private fun handleLocationPermission() {
        if (!locationPermissionUtil.isGranted()) locationPermissionUtil.requestPermission()
        locationSettingsUtil.checkLocationSettings()
    }

    private fun observeCities() {
        viewModel.citiesList.observe(viewLifecycleOwner, Observer { cities ->
            initStartLocationTextView(cities)
            initDestinationLocationTextView(cities)
        })
    }

    private fun initStartLocationTextLayout() {
        binding.startLocationLayout
            .setLocationIconListener(
                binding.startLocationTextView,
                locationProviderUtil,
                startCityListener
            ) { showCurrentLocationAsStarting(it) }
    }

    private fun showCurrentLocationAsStarting(cityModel: CityModel) {
        startCityListener.currentSelection = cityModel
        binding.startLocationTextView.text = cityModel.city.toEditable()
    }

    private fun initStartLocationTextView(cities: List<CityModel>) {
        with(binding.startLocationTextView) {
            setAdapter(getCitiesAdapter(cities))
            onItemClickListener = startCityListener
            setCitiesTextWatcher(binding.startLocationLayout, binding.startLocationTextView)
            validator = CityValidator(this, startCityListener)
        }
    }

    private fun initDestinationLocationTextView(cities: List<CityModel>) {
        with(binding.destinationLocationTextView) {
            setAdapter(getCitiesAdapter(cities))
            onItemClickListener = destinationCityListener
            validator = CityValidator(this, destinationCityListener)
        }
    }

    private fun getCitiesAdapter(cities: List<CityModel>): CitiesAdapter {
        return CitiesAdapter(requireContext(), R.layout.autocomplete_row, cities)
    }

    private fun observeFormValidation() {
        viewModel.formResult.observeNotNull(this) { result ->
            FormValidator.onResult(result)
                .ifValid { navigateToResults(result as FormResult.Complete).also { setFieldsClear() } }
                .ifInvalid { showNotSelectedFromListSnackbar(binding.root) }
                .finally { viewModel.resetFormState() }
                .validate()
        }
    }

    private fun observeDisabledLocation() {
        locationSettingsUtil.isGranted.observeNotNull(this) { isGranted ->
            if (isGranted) doOnLocationGranted()
            else doOnLocationNotGranted()
            locationSettingsUtil.resetIfGranted()
        }
    }

    private fun doOnLocationGranted() {
        binding.startLocationLayout.showEndIcon()
    }

    private fun doOnLocationNotGranted() {
        showLocationDisabledSnackbar(binding.root)
        binding.startLocationLayout.hideEndIcon()
    }

    private fun navigateToResults(formResult: FormResult.Complete) {
        SelectorFragmentDirections
            .actionSelectorFragmentToResultFragment(formResult.start, formResult.destination)
            .run { findNavController().navigate(this) }
            .run { setFieldsClear() }
    }

    private fun observeConnectionChange() {
        NetworkUtil.isNetworkAvailable.observeNotNull(this) { isConnected ->
            if (isConnected) doOnReconnected()
            else doOnDisconnected()
        }
    }

    private fun doOnReconnected() {
        binding.startLocationTextView.isEnabled = true
        binding.startLocationLayout.isEndIconVisible = true
        binding.destinationLocationTextView.isEnabled = true
    }

    private fun doOnDisconnected() {
        showEmptyDatabaseSnackbar(binding.root)
        binding.startLocationTextView.isEnabled = false
        binding.startLocationLayout.isEndIconVisible = false
        binding.destinationLocationTextView.isEnabled = false
        viewModel.resetDisconnectedEvent()
    }

    private fun observeIfDisconnectedAtViewCreation() {
        viewModel.isUnableToFetchCities.observeIfTrue(this) { doOnDisconnected() }
    }

}