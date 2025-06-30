import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tn.sesame.designsystem.R


@Composable
fun DateInputField(
    modifier: Modifier = Modifier,
    date : String,
    label : String,
    isError : Boolean = false,
    isReadOnly : Boolean = false,
    isEnabled : Boolean = false,
    onDateChanged : (date : String)->Unit
) {
  SesameTextField(
      text = date,
      label = label,
      placeholder = "" ,
      isEnabled = isEnabled,
      isReadOnly = isReadOnly,
      isError = isError ,
      onTextChanged = onDateChanged,
      rightIconRes = R.drawable.ic_calendar_outlined
  )
}