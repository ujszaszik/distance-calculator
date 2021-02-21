package com.udacity.distancecalculator.screens.locationlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.udacity.distancecalculator.common.livedata.observeNotNull
import com.udacity.distancecalculator.databinding.FragmentLocationListBinding
import com.udacity.distancecalculator.screens.mapselector.search.SearchTextListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LocationListFragment : Fragment() {

    private lateinit var binding: FragmentLocationListBinding
    private val viewModel by viewModels<LocationListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocationListBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        initRecyclerView()
        setTextSearchListener()
        setTextClearedListener()
        setHomeButtonListener()

        loadAllCities()
        observeCitiesList()

        return binding.root
    }

    private fun initRecyclerView() {
        binding.locationListRecyclerView.layoutManager = LinearLayoutManager(activity)
    }

    private fun loadAllCities() = viewModel.loadAllItems()

    private fun observeCitiesList() {
        viewModel.visibleCities.observeNotNull(this) {
            binding.locationListRecyclerView.adapter =
                LocationListAdapter().apply { submitList(it) }
            if (it.isEmpty()) showEmptyView()
            else hideEmptyView()
        }
    }

    private fun setTextClearedListener() {
        binding.locationListSearchView.setOnCloseListener {
            viewModel.doOnReset()
            false
        }
    }

    private fun setTextSearchListener() {
        SearchTextListener(
            onSubmit = { viewModel.doOnSearch(it) },
            onEmpty = { viewModel.doOnReset() }
        ).run {
            binding.locationListSearchView.setOnQueryTextListener(this)
        }
    }

    private fun hideEmptyView() {
        binding.locationListRecyclerView.visibility = VISIBLE
        binding.noItemsText.visibility = GONE
    }

    private fun showEmptyView() {
        binding.locationListRecyclerView.visibility = GONE
        binding.noItemsText.visibility = VISIBLE
    }

    private fun setHomeButtonListener() {
        binding.locationListHomeButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}