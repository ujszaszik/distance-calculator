package com.udacity.distancecalculator.loader

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.udacity.distancecalculator.MainActivity
import com.udacity.distancecalculator.R
import com.udacity.distancecalculator.common.livedata.observeIfTrue
import com.udacity.distancecalculator.common.view.startActivity
import com.udacity.distancecalculator.language.LocaleUtil

class LoaderActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoaderViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loader)
        updateTitle()
        viewModel.startLoading()
        observeNavigation()
    }

    private fun updateTitle() {
        supportActionBar?.title = getString(R.string.app_name)
    }

    private fun observeNavigation() {
        viewModel.navigateToMainScreen.observeIfTrue(this) {
            navigateToMainScreen()
            viewModel.resetNavigation()
        }
    }

    private fun navigateToMainScreen() {
        startActivity(MainActivity::class.java)
    }
}