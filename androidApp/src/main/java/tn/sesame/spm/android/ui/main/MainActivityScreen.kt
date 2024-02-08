import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import tn.sesame.spm.android.ui.login.LoginState
import tn.sesame.spm.android.ui.main.MainActivity
import tn.sesame.spm.android.ui.main.MainActivityStateHolder
import tn.sesame.spm.ui.getRegistrationBiometricIdentityIntent

@Composable
fun MainActivity.MainActivityScreen(
    modifier: Modifier = Modifier,
    uiState : MainActivityStateHolder,
    onCheckBiometricCapabilitiesStateRequest : (ActivityResult)->Unit
) {
    val biometricRegistrationActivityResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = onCheckBiometricCapabilitiesStateRequest
    )
    BiometricCapabilitiesCheckUIHandler(
        biometricCapabilitiesState = BiometricCapabilitiesUIState(
            uiState.biometricSupportState.value
        ),
        onSuccessContent = {
            if ( uiState.autoLoginState.value is LoginState.Loading){
                AutoLoginLoadingScreen(
                    modifier = modifier
                        .semantics {
                            contentDescription = "AutoLoginLoadingScreen"
                        }
                )
            } else {
                MainNavigation(
                    modifier = modifier,
                    rootNavController = uiState.rootNavController,
                    homeDestinations = uiState.homeDestinations,
                    skipLogin = uiState.autoLoginState.value is LoginState.Success
                )
            }
        },
        onQuitApp = {
            finishAffinity()
        },
        onOpenSettings = {
            biometricRegistrationActivityResultLauncher.launch(getRegistrationBiometricIdentityIntent())
        }
    )

}