package com.udacity.distancecalculator.screens.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.udacity.distancecalculator.common.livedata.observeNotNull
import com.udacity.distancecalculator.common.network.LoadingStatus
import com.udacity.distancecalculator.common.view.allGone
import com.udacity.distancecalculator.databinding.FragmentResultBinding
import com.udacity.distancecalculator.distance.model.DistanceData
import com.udacity.distancecalculator.language.LocaleUtil
import com.udacity.distancecalculator.screens.result.animation.TextAlphaAnimator
import com.udacity.distancecalculator.screens.result.animation.TickerViewInitializer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding
    private val args by navArgs<ResultFragmentArgs>()
    private val viewModel by viewModels<ResultViewModel>()
    private lateinit var distanceData: DistanceData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultBinding.inflate(inflater)

        initTickerViews()
        setHomeButtonListener()

        observeLoadingStatus()
        observeDistanceResult()

        calculateValues()

        return binding.root
    }

    private fun calculateValues() {
        distanceData = DistanceData(args.getStartingLocation(), args.getDestinationLocation())
        viewModel.calculate(distanceData)
    }

    private fun observeDistanceResult() {
        viewModel.distanceResult.observeNotNull(this) {
            binding.result = it
            viewModel.resetDistanceResult()
        }
    }

    private fun observeLoadingStatus() {
        viewModel.loadingStatus.observeNotNull(this) { status ->
            when (status) {
                is LoadingStatus.Loading -> onLoading()
                is LoadingStatus.Success -> onResultSuccess()
                is LoadingStatus.Error -> onError()
                is LoadingStatus.Disconnected -> onDisconnected()
                is LoadingStatus.NoResults -> onNoResults()
            }
        }
    }

    private fun onLoading() {
        binding.resultScreen.visibility = GONE
        binding.loadingScreen.visibility = VISIBLE
    }

    private fun onResultSuccess() {
        binding.resultScreen.visibility = VISIBLE
        binding.loadingScreen.visibility = GONE
        startTextAnimations()
    }

    private fun onError() {
        binding.loadingScreen.visibility = GONE
        binding.errorScreen.visibility = VISIBLE
    }

    private fun onDisconnected() {
        hideNetworkCalculatedResultsViews()
        binding.disconnectedScreen.visibility = VISIBLE
        viewModel.calculateOffline(distanceData)
    }

    private fun onNoResults() {
        binding.loadingScreen.visibility = GONE
        binding.noResultsScreen.visibility = VISIBLE
        hideNetworkCalculatedResultsViews()
        viewModel.calculateOffline(distanceData)
    }

    private fun hideNetworkCalculatedResultsViews() {
        allGone(
            binding.carImageView, binding.carTickerView, binding.carTimeTextView,
            binding.bikeImageView, binding.bikeTickerView, binding.bikeTimeTextView,
            binding.walkImageView, binding.walkTickerView, binding.walkTimeTextView,
            binding.secondDivider, binding.thirdDivider
        )
    }

    private fun initTickerViews() {
        TickerViewInitializer.init(binding.airPlaneTickerView)
        TickerViewInitializer.init(binding.carTickerView)
        TickerViewInitializer.init(binding.bikeTickerView)
        TickerViewInitializer.init(binding.walkTickerView)
    }

    private fun startTextAnimations() {
        TextAlphaAnimator.startAnimationOn(binding.carTimeTextView)
        TextAlphaAnimator.startAnimationOn(binding.bikeTimeTextView)
        TextAlphaAnimator.startAnimationOn(binding.walkTimeTextView)
    }

    private fun setHomeButtonListener() {
        binding.homeButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}