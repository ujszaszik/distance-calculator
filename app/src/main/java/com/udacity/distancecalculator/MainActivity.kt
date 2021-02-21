package com.udacity.distancecalculator

import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.udacity.distancecalculator.language.LocaleUtil
import com.udacity.distancecalculator.location.LocationChangedReceiver
import com.udacity.distancecalculator.location.LocationPermissionUtil
import com.udacity.distancecalculator.location.LocationRequestCode
import com.udacity.distancecalculator.location.LocationSettingsUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var locationSettingsUtil: LocationSettingsUtil

    @Inject
    lateinit var locationPermissionUtil: LocationPermissionUtil

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LocaleUtil.updateLanguage(this)
        setContentView(R.layout.activity_main)
        setUpNavHost()
        setUpNavController()
        registerLocationChangedReceiver()
    }

    private fun setUpNavHost() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    }

    private fun setUpNavController() {
        navController = navHostFragment.navController.apply {
            NavigationUI.setupActionBarWithNavController(this@MainActivity, this)
        }
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()

    override fun onBackPressed() {
        if (!isStartingDestination()) super.onBackPressed()
    }

    private fun isStartingDestination(): Boolean {
        return navController.currentDestination?.id == R.id.selectorFragment
    }

    private fun registerLocationChangedReceiver() {
        val locationChangedListener = LocationChangedReceiver(locationSettingsUtil)
        val providersChangedFilter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        registerReceiver(locationChangedListener, providersChangedFilter)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == locationSettingsUtil.requestCode) {
            locationSettingsUtil.checkLocationSettings(false)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LocationRequestCode.forCurrentApiLevel()) {
            val isGranted = grantResults.contains(PackageManager.PERMISSION_GRANTED)
            locationPermissionUtil.updateGrantState(isGranted)
        }
    }
}