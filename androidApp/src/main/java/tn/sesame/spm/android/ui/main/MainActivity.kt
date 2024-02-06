package tn.sesame.spm.android.ui.main

import MainActivityScreen
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.viewmodel.ext.android.getViewModel
import tn.sesame.designsystem.SesameTheme
import tn.sesame.designsystem.components.bars.SesameBottomNavigationBarDefaults
import tn.sesame.spm.android.ui.login.LoginState

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


