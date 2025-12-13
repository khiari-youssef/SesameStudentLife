package com.youapps.onlybeans.android.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.rememberNavController
import com.youapps.designsystem.OBTheme
import com.youapps.designsystem.components.bars.OBBottomNavigationBarDefaults
import com.youapps.onlybeans.data.repositories.AppMetaDataAPI
import com.youapps.onlybeans.di.AppMetaDataAPITag
import com.youapps.users_management.ui.login.LoginState
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {

    private lateinit var _viewModel: MainActivityViewModel

    private val appMetaDataAPI : AppMetaDataAPI by inject<AppMetaDataAPI>(AppMetaDataAPITag)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                appMetaDataAPI.initAppData()
            }
        }

        _viewModel = getViewModel()
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            _viewModel.autoLoginState.value == LoginState.Loading
        }
        setContent {
            val autoLoginState = _viewModel.autoLoginState.collectAsStateWithLifecycle()

            val uiState = MainActivityStateHolder
                .rememberMainActivityState(
                    biometricSupportState = _viewModel.biometricCapabilitiesState
                        .collectAsStateWithLifecycle(),
                    rootNavController = rememberNavController(),
                    homeDestinations = _viewModel.navigationBarState.map {
                        OBBottomNavigationBarDefaults(it)
                    }.collectAsState(OBBottomNavigationBarDefaults.DEFAULT)
                )

            OBTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    MainNavigation(
                        modifier = Modifier.fillMaxSize(),
                        rootNavController = uiState.rootNavController,
                        homeDestinations = uiState.homeDestinations,
                        autoLoginState = autoLoginState.value
                    )
                }
            }
        }


    }

    fun updateBadgeCount(itemIndex: Int, count: Int) {
        _viewModel.updateBadgeCount(itemIndex, count)
    }
}


