import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.koinInject
import tn.sesame.spm.android.R
import tn.sesame.spm.security.BiometricAuthService
import tn.sesame.spm.security.BiometricLauncherService
import tn.sesame.spm.security.SupportedDeviceAuthenticationMethods
import tn.sesame.spm.ui.getRegistrationBiometricIdentityIntent

@Composable
fun RequireBiometricAuth(
    bioService : BiometricAuthService = koinInject(),
    bioAuthTitle : String =  stringResource(id = R.string.biometric_auth_dialog_message),
    bioAuthSubtitle : String = stringResource(id = R.string.biometric_auth_dialog_title),
    onBiometricPassResult : (BiometricLauncherService.DeviceAuthenticationState)->Unit
) {
    val activityResultLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult(), onResult ={
        if (it.resultCode == FragmentActivity.RESULT_OK){
            onBiometricPassResult(BiometricLauncherService.DeviceAuthenticationState.Idle)
        }
    } )
    val localContext = LocalContext.current
    val hasBioCapabilities = bioService.checkBiometricCapabilitiesState()
    if (hasBioCapabilities is SupportedDeviceAuthenticationMethods.Available){
       val result = hasBioCapabilities.biometricLauncherService.launch(
                activity = localContext as FragmentActivity,
                title = bioAuthTitle,
                subtitle = bioAuthSubtitle
            ).collectAsStateWithLifecycle(
            initialValue = BiometricLauncherService.DeviceAuthenticationState.Idle,
           )
        LaunchedEffect(key1 = result.value, block = {
            onBiometricPassResult(result.value)
        })
    } else {
        activityResultLauncher.launch(getRegistrationBiometricIdentityIntent())
    }
}