import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.youapps.designsystem.R

@Preview
@Composable
fun ServerErrorModal(
    details : String = stringResource(id = R.string.error_network_internal_server_details),
    onRetryAction : ()->Unit={}
) {
    ErrorModal(
        title = stringResource(id = R.string.error_network_title),
        details = details,
        imgRes = R.drawable.ic_server_error,
        onRetryAction = onRetryAction
    )
}