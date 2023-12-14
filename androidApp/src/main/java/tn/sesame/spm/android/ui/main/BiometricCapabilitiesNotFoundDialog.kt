import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import tn.sesame.spm.android.R

@Composable
fun BiometricCapabilitiesNotFoundDialog(
   isShown : Boolean = false,
   onClosed : ()->Unit
) {
   InfoPopup(
      title = stringResource(id = R.string.error_unsupported_biometric_features_title),
      subtitle = stringResource(id = R.string.error_unsupported_biometric_features_message),
      isShown = isShown,
      buttonText = stringResource(id = tn.sesame.designsystem.R.string.ok),
      onDismissRequest = onClosed,
      onButtonClicked = onClosed
   )
}

@Composable
fun BiometricIdentityNotRegisteredDialog(
   isShown : Boolean = false,
   onOpenSettings : ()->Unit,
   onClosed : ()->Unit
) {
   InfoPopup(
      title = stringResource(id = R.string.error_unregistered_biometric_identity_title),
      subtitle = stringResource(id = R.string.error_unregistered_biometric_identity_message),
      isShown = isShown,
      buttonText = stringResource(id = R.string.button_open_system_settings),
      onDismissRequest = onClosed,
      onButtonClicked = onOpenSettings
   )
}