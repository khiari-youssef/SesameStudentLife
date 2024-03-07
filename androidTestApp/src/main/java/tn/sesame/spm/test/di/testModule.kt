package tn.sesame.spm.test.di

import org.koin.dsl.module
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import tn.sesame.spm.security.BiometricLauncherService
import javax.crypto.SecretKey

val testModule = module {
  factory<BiometricLauncherService> {
      object : BiometricLauncherService{
          override val authenticationResultState: StateFlow<BiometricLauncherService.DeviceAuthenticationState> = MutableStateFlow(
              BiometricLauncherService.DeviceAuthenticationState.Idle
          )
          override fun launch(
              activity: FragmentActivity,
              title: String,
              subtitle: String
          ): Flow<BiometricLauncherService.DeviceAuthenticationState> = flow {
              emit(BiometricLauncherService.DeviceAuthenticationState.Idle)
          }

          override fun launch(
              fragment: Fragment,
              title: String,
              subtitle: String
          ): Flow<BiometricLauncherService.DeviceAuthenticationState> = flow {
              emit(BiometricLauncherService.DeviceAuthenticationState.Idle)
          }

          override fun launchAndEncrypt(
              activity: FragmentActivity,
              secretKey: SecretKey,
              title: String,
              subtitle: String
          ): Flow<BiometricLauncherService.DeviceAuthenticationState>  = flow{
              emit(BiometricLauncherService.DeviceAuthenticationState.Idle)
          }

      }
  }
}