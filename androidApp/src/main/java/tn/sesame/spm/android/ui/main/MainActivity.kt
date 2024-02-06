package tn.sesame.spm.android.ui.main

import AutoLoginLoadingScreen
import BiometricCapabilitiesCheckUIHandler
import BiometricCapabilitiesNotFoundDialog
import BiometricCapabilitiesUIState
import BiometricIdentityNotRegisteredDialog
import InfoPopup
import MainActivityScreen
import MainNavigation
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel
import tn.sesame.designsystem.SesameFontFamilies
import tn.sesame.designsystem.SesameTheme
import tn.sesame.designsystem.components.bars.SesameBottomNavigationBarDefaults
import tn.sesame.spm.android.R
import tn.sesame.spm.android.ui.login.LoginState
import tn.sesame.spm.security.BiometricAuthService
import tn.sesame.spm.security.SupportedDeviceAuthenticationMethods
import tn.sesame.spm.ui.getRegistrationBiometricIdentityIntent

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            val viewModel : MainActivityViewModel = getViewModel()
            val uiState = MainActivityStateHolder
                .rememberMainActivityState(
                    biometricSupportState = viewModel.biometricCapabilitiesState
                        .collectAsStateWithLifecycle(),
                    autoLoginState = viewModel.autoLoginState.collectAsStateWithLifecycle(
                        initialValue = LoginState.Loading
                    ),
                    rootNavController =rememberNavController() ,
                    homeDestinations = SesameBottomNavigationBarDefaults.getDefaultConfiguration()
                )

            SesameTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainActivityScreen(
                        modifier = Modifier.fillMaxSize(),
                        uiState = uiState,
                        onCheckBiometricCapabilitiesStateRequest = {
                            viewModel.checkBiometricCapabilitiesState()
                        }
                    )
                }
            }
        }
    }
}


