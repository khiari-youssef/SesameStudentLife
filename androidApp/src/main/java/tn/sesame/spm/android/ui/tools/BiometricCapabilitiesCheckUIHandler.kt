import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.flow.update
import tn.sesame.spm.android.R
import tn.sesame.spm.android.ui.main.MainActivity
import tn.sesame.spm.security.SupportedDeviceAuthenticationMethods
import tn.sesame.spm.ui.getRegistrationBiometricIdentityIntent

@Stable
@JvmInline
value class BiometricCapabilitiesUIState(
    val state : SupportedDeviceAuthenticationMethods
)

@Composable
fun BiometricCapabilitiesCheckUIHandler(
    biometricCapabilitiesState : BiometricCapabilitiesUIState,
    onOpenSettings : ()->Unit,
    onQuitApp : ()->Unit,
    onSuccessContent : @Composable ()->Unit
) {
  when (biometricCapabilitiesState.state){
      is SupportedDeviceAuthenticationMethods.Available->{
           onSuccessContent()
      }
      is SupportedDeviceAuthenticationMethods.Unavailable ->{
          BiometricIdentityNotRegisteredDialog(
              isShown = true,
              onClosed = onQuitApp,
              onOpenSettings = onOpenSettings
          )
      }
      is SupportedDeviceAuthenticationMethods.Undefined ->{
          InfoPopup(
              title = stringResource(id = R.string.error_biometric_temporararely_unavailable_title),
              subtitle = stringResource(id = R.string.error_biometric_temporararely_unavailable_message),
              isShown = true ,
              buttonText = stringResource(id = tn.sesame.designsystem.R.string.retry) ,
              onButtonClicked = onOpenSettings,
              onDismissRequest = onQuitApp
          )
      }
      is SupportedDeviceAuthenticationMethods.Waiting ->{

      }
      is SupportedDeviceAuthenticationMethods.HardwareUnavailable->{
          InfoPopup(
              title = stringResource(id = R.string.error_biometric_undefined_title),
              subtitle = stringResource(id = R.string.error_biometric_undefined_message),
              isShown = true ,
              buttonText = stringResource(id = tn.sesame.designsystem.R.string.retry) ,
              onButtonClicked = onOpenSettings,
              onDismissRequest = onQuitApp
          )
      }
      is SupportedDeviceAuthenticationMethods.NoHardware ->{
          BiometricCapabilitiesNotFoundDialog(
              isShown = true,
              onClosed = onQuitApp
          )
      }
  }
}