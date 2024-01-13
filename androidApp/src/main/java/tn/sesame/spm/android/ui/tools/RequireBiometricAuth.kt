import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import tn.sesame.spm.android.base.NavigationRoutingData
import tn.sesame.spm.security.BiometricAuthService
import tn.sesame.spm.security.BiometricLauncherService
import tn.sesame.spm.security.SupportedDeviceAuthenticationMethods
import tn.sesame.spm.ui.getRegistrationBiometricIdentityIntent

@Composable
fun RequireBiometricAuth(
    bioService : BiometricAuthService = koinInject(),
    bioAuthTitle : String,
    bioAuthSubtitle : String,
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