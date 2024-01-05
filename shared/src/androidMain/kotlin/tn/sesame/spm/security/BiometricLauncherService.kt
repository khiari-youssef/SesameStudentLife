package tn.sesame.spm.security

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.crypto.SecretKey

interface BiometricLauncherService {


    val authenticationResultState : StateFlow<DeviceAuthenticationState>
    fun launch(activity: FragmentActivity,title : String,
               subtitle : String) : Flow<DeviceAuthenticationState>

    fun launch(fragment: Fragment,title : String,
               subtitle : String) : Flow<DeviceAuthenticationState>

    fun launchAndEncrypt(activity: FragmentActivity,secretKey: SecretKey,title : String,
                         subtitle : String) : Flow<DeviceAuthenticationState>


    sealed interface DeviceAuthenticationState{

        data object Idle : DeviceAuthenticationState
        data object Failed : DeviceAuthenticationState
        data class Error(val code : Int) : DeviceAuthenticationState{
            override fun equals(other: Any?): Boolean {
                return other is Error && other.code == this.code
            }

            override fun hashCode(): Int {
                return code
            }
        }
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
    data object Waiting : SupportedDeviceAuthenticationMethods
    data object Undefined : SupportedDeviceAuthenticationMethods
    data object NoHardware : SupportedDeviceAuthenticationMethods
    data object HardwareUnavailable : SupportedDeviceAuthenticationMethods
    data object Unavailable : SupportedDeviceAuthenticationMethods


    data class Available( val biometricLauncherService: BiometricLauncherService) : SupportedDeviceAuthenticationMethods
}