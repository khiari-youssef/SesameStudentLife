import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import tn.sesame.designsystem.R

@Composable
fun AppExitPopup(
    isShown : Boolean,
   onConfirmAppExit : ()->Unit,
   onCancelled : ()->Unit
) {
    DualButtonPopup(
        isShown =isShown,
        title = stringResource(id = R.string.app_exit_popup_title),
        onDismissRequest = onCancelled,
        onNegativeButtonClicked = onCancelled,
        onPositiveButtonClicked =onConfirmAppExit,
        positiveButtonText = stringResource(id = R.string.yes),
        negativeButtonText = stringResource(id = R.string.no)
    )
}