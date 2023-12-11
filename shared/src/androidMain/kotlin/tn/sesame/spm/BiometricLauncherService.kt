package com.example.androidutils.systemServices.security.deviceAuthentication

import androidx.fragment.app.FragmentActivity
import javax.crypto.SecretKey

interface BiometricLauncherService {

    fun launch(activity: FragmentActivity)

    fun launchAndEncrypt(activity: FragmentActivity,secretKey: SecretKey)

    fun setOnAuthenticationActionListener(callback : (DeviceAuthenticationState)->Unit)

    sealed interface DeviceAuthenticationState{
        object Failed : DeviceAuthenticationState
        data class Error(val code : Int) : DeviceAuthenticationState
        data class Success(
            val method : Int,
            val data : ByteArray?=null
            ) : DeviceAuthenticationState {
            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as Success

                if (method != other.method) return false
                if (data != null) {
                    if (other.data == null) return false
                    if (!data.contentEquals(other.data)) return false
                } else if (other.data != null) return false

                return true
            }

            override fun hashCode(): Int {
                var result = method
                result = 31 * result + (data?.contentHashCode() ?: 0)
                return result
            }
        }
    }
}

sealed interface SupportedDeviceAuthenticationMethods{
    data object Undefined : SupportedDeviceAuthenticationMethods
    data object NoHardware : SupportedDeviceAuthenticationMethods
    data object HardwareUnavailable : SupportedDeviceAuthenticationMethods
    data object Unavailable : SupportedDeviceAuthenticationMethods
    data class Available( val biometricLauncherService: BiometricLauncherService) : SupportedDeviceAuthenticationMethods
}