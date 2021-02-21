package com.udacity.distancecalculator.screens.settings

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.udacity.distancecalculator.MainActivity
import com.udacity.distancecalculator.R
import com.udacity.distancecalculator.common.livedata.observeIfTrue
import com.udacity.distancecalculator.common.view.startActivity
import com.udacity.distancecalculator.databinding.ActivitySettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private val viewModel by viewModels<SettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)

        updateTitle()
        setUpHomeButtonListener()
        observeHomeScreenNavigation()
    }

    private fun updateTitle() {
        supportActionBar?.title = getString(R.string.app_name)
    }

    override fun onBackPressed() = playExitAnimation()

    private fun setUpHomeButtonListener() {
        binding.settingsHomeButton.setOnClickListener { viewModel.onHomeScreenRequest() }
    }

    private fun observeHomeScreenNavigation() {
        viewModel.isHomeScreenRequested.observeIfTrue(this) {
            playExitAnimation()
            viewModel.resetHomeScreenRequest()
        }
    }

    private fun playExitAnimation() {
        binding.motionLayout.run {
            transitionToStart()
            setTransitionListener(TransitionFinishedListener { navigateBackToMainScreen() })
        }
    }

    private fun navigateBackToMainScreen() {
        startActivity(MainActivity::class.java)
    }

}