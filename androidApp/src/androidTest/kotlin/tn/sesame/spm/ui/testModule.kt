package tn.sesame.spm.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.koin.dsl.module
import tn.sesame.spm.security.BiometricLauncherService
import javax.crypto.SecretKey

val testModule = module {
  factory<BiometricLauncherService> {
      object : BiometricLauncherService{
          override val authenticationResultState: StateFlow<BiometricLauncherService.DeviceAuthenticationState>
              get() = TODO("Not yet implemented")

          override fun launchAndEncrypt(
              activity: FragmentActivity,
              secretKey: SecretKey,
              title: String,
              subtitle: String
          ): Flow<BiometricLauncherService.DeviceAuthenticationState> {
              TODO("Not yet implemented")
          }

          override fun launch(
              fragment: Fragment,
              title: String,
              subtitle: String
          ): Flow<BiometricLauncherService.DeviceAuthenticationState> {
              TODO("Not yet implemented")
          }

          override fun launch(
              activity: FragmentActivity,
              title: String,
              subtitle: String
          ): Flow<BiometricLauncherService.DeviceAuthenticationState> {
              TODO("Not yet implemented")
          }
      }
  }
}