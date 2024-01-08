import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import tn.sesame.spm.android.R
import tn.sesame.spm.security.BiometricLauncherService

@Composable
fun BiometricAuthUIHandler(
    state : BiometricLauncherService.DeviceAuthenticationState,
    onSuccessContent : @Composable ()->Unit
) {

    when (state){
        is BiometricLauncherService.DeviceAuthenticationState.Error ->{
            val isShown = remember {
                mutableStateOf(true)
            }
            InfoPopup(
                title = stringResource(id = R.string.biometric_auth_cancelled_title) ,
                subtitle = stringResource(id = R.string.biometric_auth_cancelled_message),
                isShown = isShown.value,
                buttonText =  stringResource(id = tn.sesame.designsystem.R.string.ok),
                onButtonClicked = {
                    isShown.value = false
                }) {
                isShown.value = false
            }
        }
        is BiometricLauncherService.DeviceAuthenticationState.Failed ->{
            val isShown = remember {
                mutableStateOf(true)
            }
            InfoPopup(
                title = stringResource(id = R.string.biometric_auth_failed_title),
                subtitle = stringResource(id = R.string.biometric_auth_failed_message),
                isShown = isShown.value,
                buttonText = stringResource(id = tn.sesame.designsystem.R.string.ok),
                onButtonClicked = {
                    isShown.value = true
                }) {
                isShown.value = true
            }
        }
        is BiometricLauncherService.DeviceAuthenticationState.Success ->{
            onSuccessContent()
        }
        is BiometricLauncherService.DeviceAuthenticationState.Idle ->{

        }
    }
}