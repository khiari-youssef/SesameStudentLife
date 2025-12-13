package com.youapps.onlybeans.platform

import android.Manifest.permission
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationManager.FUSED_PROVIDER
import android.location.LocationRequest
import android.location.LocationRequest.QUALITY_BALANCED_POWER_ACCURACY
import android.location.LocationRequest.QUALITY_HIGH_ACCURACY
import android.os.CancellationSignal
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import androidx.annotation.RequiresPermission
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.update


data class LocationState(
    val latitude: Double,
    val longitude: Double,
    val accuracy: Float,
    val altitude: Double? = null,
    val timestamp: Long
)

enum class LocationUpdateStrategy{
    POWER_EFFICIENCY,
    HIGH_ACCURACY
}


class OBLocationService(val applicationContext: Context) : LocationListener {

    private val _locationManager: LocationManager =
        applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager


    private val _defaultPowerEfficientLocationRequest = LocationRequest
        .Builder(3000)
        .setMinUpdateDistanceMeters(5f)
        .setQuality(QUALITY_BALANCED_POWER_ACCURACY)
        .build()

    private val _defaultHighAccuracyLocationRequest = LocationRequest
        .Builder(1000)
        .setMinUpdateDistanceMeters(2f)
        .setQuality(QUALITY_HIGH_ACCURACY)
        .build()

    private val _locationStateFlow: MutableStateFlow<LocationState?> =
        MutableStateFlow(value = null)


    @RequiresPermission(anyOf = [permission.ACCESS_COARSE_LOCATION, permission.ACCESS_FINE_LOCATION])
    fun isLocationEnabled(): Boolean = _locationManager.isLocationEnabled



    @RequiresPermission(anyOf = [permission.ACCESS_COARSE_LOCATION, permission.ACCESS_FINE_LOCATION])
    fun subscribe(strategy : LocationUpdateStrategy): StateFlow<LocationState?> {
        _locationManager.requestLocationUpdates(
            FUSED_PROVIDER,
            when(strategy){
                LocationUpdateStrategy.POWER_EFFICIENCY -> _defaultPowerEfficientLocationRequest
                LocationUpdateStrategy.HIGH_ACCURACY -> _defaultHighAccuracyLocationRequest
            },
            applicationContext.mainExecutor,
            this
        )
        return _locationStateFlow;
    }



    @RequiresPermission(anyOf = [permission.ACCESS_COARSE_LOCATION, permission.ACCESS_FINE_LOCATION])
    fun getSingleFreshLocation(): Flow<LocationState> = callbackFlow {
        val cancellationSignal = CancellationSignal()
        _locationManager.getCurrentLocation(
            FUSED_PROVIDER,
            _defaultHighAccuracyLocationRequest,
            cancellationSignal,
            applicationContext.mainExecutor
        ) {
            trySend(
                LocationState(
                    latitude = it.latitude,
                    longitude = it.longitude,
                    accuracy = it.accuracy,
                    altitude = it.altitude,
                    timestamp = it.time
                )
            )
        }
        awaitClose {
            cancellationSignal.cancel()
        }
    }


    fun unSubscribe() {
        _locationStateFlow.update {
            null
        }
        _locationManager.removeUpdates(this)
    }

    override fun onLocationChanged(location: Location) {
        _locationStateFlow.update {
            LocationState(
                latitude = location.latitude,
                longitude = location.longitude,
                accuracy = location.accuracy,
                altitude = location.altitude,
                timestamp = location.time
            )
        }
    }

    fun getRedirectionToLocationSettingsIntent(context: Context) = Intent(ACTION_LOCATION_SOURCE_SETTINGS).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }


}