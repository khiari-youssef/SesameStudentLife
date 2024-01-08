import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import tn.sesame.spm.android.R
import tn.sesame.spm.android.ui.profile.UserProfileDetails
import tn.sesame.spm.domain.entities.SesameUser

@Composable
fun ViewUserProfilePopup(
    sesameUser : SesameUser,
    isShown : Boolean,
    onDismissRequest : ()->Unit
) {
    if (isShown){
        val localContext = LocalContext.current
        Dialog(onDismissRequest = onDismissRequest) {
            val title = stringResource(id = R.string.profile_send_email_via,"Ahmed")
             UserProfileDetails(
                 sesameUser = sesameUser,
                 onProfileEmailClicked = {email->
                     val intent = Intent(Intent.ACTION_VIEW)
                     val data = Uri.parse(
                         "mailto:$email?subject=" + Uri.encode("") + "&body=" + Uri.encode(
                             ""
                         )
                     )
                     intent.setData(data)
                     ContextCompat.startActivity(
                         localContext, Intent.createChooser(
                             intent, title
                         ), Bundle()
                     )
                 }
             )
        }
    }
}