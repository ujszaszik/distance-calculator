package com.udacity.distancecalculator.location

import android.app.Activity
import android.content.Context
import android.location.Geocoder
import com.google.android.gms.location.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import java.util.*


@Module
@InstallIn(ActivityComponent::class)
class LocationModule {

    @Provides
    @ActivityScoped
    fun provideLocationPermissionUtil(activity: Activity): LocationPermissionUtil {
        return LocationPermissionUtil(activity)
    }

    @Provides
    fun provideLocationRequest(): LocationRequest {
        return LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_LOW_POWER
        }
    }

    @Provides
    @ActivityScoped
    fun provideLocationSettingsUtil(
        activity: Activity,
        settingsClient: SettingsClient,
        requestBuilder: LocationSettingsRequest.Builder
    ): LocationSettingsUtil {
        return LocationSettingsUtil(activity, settingsClient, requestBuilder)
    }

    @Provides
    fun provideLocationSettingsRequestBuilder(locationRequest: LocationRequest): LocationSettingsRequest.Builder {
        return LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
    }

    @Provides
    fun provideLocationSettingsClient(activity: Activity): SettingsClient {
        return LocationServices.getSettingsClient(activity)
    }

    @Provides
    @ActivityScoped
    fun provideFusedLocationProvider(activity: Activity): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(activity)
    }

    @Provides
    @ActivityScoped
    fun provideLocationProviderUtil(
        fusedLocationProviderClient: FusedLocationProviderClient,
        geocoder: Geocoder
    ): LocationProviderUtil {
        return LocationProviderUtil(fusedLocationProviderClient, geocoder)
    }

    @Provides
    @ActivityScoped
    fun provideGeocoder(@ApplicationContext context: Context): Geocoder {
        return Geocoder(context, Locale.getDefault())
    }

}