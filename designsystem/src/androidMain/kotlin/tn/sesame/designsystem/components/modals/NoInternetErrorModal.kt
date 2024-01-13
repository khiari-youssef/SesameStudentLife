import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import tn.sesame.designsystem.R

@Preview
@Composable
fun NoInternetErrorModal(
    details : String = stringResource(id = R.string.error_network_no_internet_details),
    onRetryAction : ()->Unit={}
) {
    ErrorModal(
        title = stringResource(id = R.string.error_network_title),
        details = details,
        imgRes = R.drawable.ic_no_internet,
        onRetryAction = onRetryAction
    )
}