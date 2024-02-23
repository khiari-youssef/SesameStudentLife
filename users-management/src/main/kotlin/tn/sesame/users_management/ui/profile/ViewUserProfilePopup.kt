import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import tn.sesame.users_management.ui.profile.UserProfileDetails
import tn.sesame.spm.domain.entities.SesameUser
import tn.sesame.users_management.R

@Composable
fun ViewUserProfilePopup(
    sesameUser : SesameUser?,
    isShown : Boolean,
    onDismissRequest : ()->Unit
) {
    if (isShown && sesameUser != null){
        val localContext = LocalContext.current
        Dialog(onDismissRequest = onDismissRequest) {
            val title = stringResource(id = R.string.profile_send_email_via,"Ahmed")
             UserProfileDetails(
                 modifier = Modifier.background(
                     color = MaterialTheme.colorScheme.surfaceVariant,
                     shape = RoundedCornerShape(3.dp)
                 ),
                 showSex = false,
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