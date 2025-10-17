package tn.sesame.spm.android.ui.main

import MainActivityScreen
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.map
import org.koin.androidx.viewmodel.ext.android.getViewModel
import tn.sesame.designsystem.SesameTheme
import tn.sesame.designsystem.components.bars.SesameBottomNavigationBarDefaults
import tn.sesame.users_management.ui.login.LoginState

class MainActivity : FragmentActivity() {

    private  lateinit var _viewModel : MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewModel = getViewModel()
        installSplashScreen()
        setContent {

            val uiState = MainActivityStateHolder
                .rememberMainActivityState(
                    biometricSupportState = _viewModel.biometricCapabilitiesState
                        .collectAsStateWithLifecycle(),
                    autoLoginState = _viewModel.autoLoginState.collectAsStateWithLifecycle(
                        initialValue = LoginState.Loading
                    ),
                    rootNavController =rememberNavController() ,
                    homeDestinations = _viewModel.navigationBarState.map {
                        SesameBottomNavigationBarDefaults(it)
                    }.collectAsState(SesameBottomNavigationBarDefaults.DEFAULT)
                )

            SesameTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    MainActivityScreen(
                        modifier = Modifier.fillMaxSize(),
                        uiState = uiState,
                        onCheckBiometricCapabilitiesStateRequest = {
                            _viewModel.checkBiometricCapabilitiesState()
                        }
                    )
                }
            }
        }
    }

      fun updateBadgeCount(itemIndex : Int,count : Int){
        _viewModel.updateBadgeCount(itemIndex,count)
    }
}


