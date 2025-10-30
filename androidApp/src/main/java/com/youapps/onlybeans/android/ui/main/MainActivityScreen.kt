import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.youapps.onlybeans.android.ui.main.MainActivity
import com.youapps.onlybeans.android.ui.main.MainActivityStateHolder
import com.youapps.onlybeans.ui.getRegistrationBiometricIdentityIntent
import com.youapps.users_management.ui.login.LoginState

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