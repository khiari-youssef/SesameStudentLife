import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import tn.sesame.designsystem.components.DetailsScreenTemplate
import tn.sesame.users_management.R
import tn.sesame.users_management.ui.settings.AppSettingsStateHolder

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    uiState : AppSettingsStateHolder,
    onItemSelectedStateChanged : (isSelected : Boolean)->Unit,
    onBackPressed : ()->Unit
) {
 DetailsScreenTemplate(
     modifier =modifier,
     title = stringResource(id = R.string.settings_screen_title),
     onBackPressed = onBackPressed
 ) {
   Column(
       horizontalAlignment = Alignment.Start,
       verticalArrangement = Arrangement
           .spacedBy(12.dp,Alignment.CenterVertically)
   ) {
       SesameCheckableSettingsOption(
           modifier = Modifier
               .fillMaxWidth()
               .wrapContentHeight(),
           isEnabled = true,
           isSelected = uiState.isAutoLoginEnabled.value,
           label = stringResource(id = R.string.settings_auto_login_option_label),
           onItemSelectedStateChanged = onItemSelectedStateChanged
       )
   }
 }
}