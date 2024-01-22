package tn.sesame.spm.android.ui.main

import BiometricCapabilitiesNotFoundDialog
import BiometricIdentityNotRegisteredDialog
import InfoPopup
import MainNavigation
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.koin.android.ext.android.inject
import tn.sesame.designsystem.SesameTheme
import tn.sesame.designsystem.components.bars.SesameBottomNavigationBarDefaults
import tn.sesame.spm.android.R
import tn.sesame.spm.security.BiometricAuthService
import tn.sesame.spm.security.SupportedDeviceAuthenticationMethods
import tn.sesame.spm.ui.getRegistrationBiometricIdentityIntent

class MainActivity : FragmentActivity() {

    private val bioService : BiometricAuthService by inject()
    private var biometricRegistrationActivityResultLauncher : ActivityResultLauncher<Intent>?=null
    private val biometricCapabilitiesState : MutableStateFlow<SupportedDeviceAuthenticationMethods>
    = MutableStateFlow(SupportedDeviceAuthenticationMethods.Waiting)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        biometricRegistrationActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                biometricCapabilitiesState.update {
                    bioService.checkBiometricCapabilitiesState()
                }
        }
        biometricCapabilitiesState.update {
            bioService.checkBiometricCapabilitiesState()
        }
        installSplashScreen()
        setContent {
            val biometricSupportState : State<SupportedDeviceAuthenticationMethods>  = biometricCapabilitiesState
                .collectAsStateWithLifecycle()
            val rootNavController = rememberNavController()
            val homeDestinations = SesameBottomNavigationBarDefaults.getDefaultConfiguration()

            SesameTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when (biometricSupportState.value){
                        is SupportedDeviceAuthenticationMethods.Available ->{
                            MainNavigation(
                                modifier = Modifier,
                                rootNavController = rootNavController,
                                homeDestinations = homeDestinations
                            )
                        }
                        is SupportedDeviceAuthenticationMethods.Unavailable ->{
                            BiometricIdentityNotRegisteredDialog(
                                isShown = true,
                                onClosed = {
                                     this@MainActivity.finishAffinity()
                                },
                                onOpenSettings = {
                                    biometricRegistrationActivityResultLauncher
                                        ?.launch(getRegistrationBiometricIdentityIntent())
                                }
                            )
                        }
                        is SupportedDeviceAuthenticationMethods.NoHardware ->{
                            BiometricCapabilitiesNotFoundDialog(
                                isShown = true,
                                onClosed = {
                                    this@MainActivity.finishAffinity()
                                }
                            )
                        }
                        is SupportedDeviceAuthenticationMethods.HardwareUnavailable->{
                            InfoPopup(
                                title = stringResource(id = R.string.error_biometric_undefined_title),
                                subtitle = stringResource(id = R.string.error_biometric_undefined_message),
                                isShown = true ,
                                buttonText = stringResource(id = tn.sesame.designsystem.R.string.retry) ,
                                onButtonClicked = {
                                    biometricCapabilitiesState.update {
                                        bioService.checkBiometricCapabilitiesState()
                                    }
                                }) {
                                this@MainActivity.finishAffinity()
                            }
                        }
                        is SupportedDeviceAuthenticationMethods.Undefined ->{
                                 InfoPopup(
                                     title = stringResource(id = R.string.error_biometric_temporararely_unavailable_title),
                                     subtitle = stringResource(id = R.string.error_biometric_temporararely_unavailable_message),
                                     isShown = true ,
                                     buttonText = stringResource(id = tn.sesame.designsystem.R.string.retry) ,
                                     onButtonClicked = {
                                         biometricCapabilitiesState.update {
                                             bioService.checkBiometricCapabilitiesState()
                                         }
                                     }) {
                                      this@MainActivity.finishAffinity()
                                 }
                        }
                        is SupportedDeviceAuthenticationMethods.Waiting ->{

                        }
                    }
                }
            }
        }
    }
}


