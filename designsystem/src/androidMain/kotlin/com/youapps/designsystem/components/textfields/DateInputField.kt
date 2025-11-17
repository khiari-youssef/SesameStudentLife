import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.youapps.designsystem.R


@Composable
fun DateInputField(
    modifier: Modifier = Modifier,
    date : String,
    label : String,
    errorMessage : String?=null,
    isEnabled : Boolean = false,
    onDateChanged : (date : String)->Unit
) {
    OBTextField(
        modifier = modifier,
      text = date,
      label = label,
      placeholder = "" ,
      isEnabled = isEnabled,
      errorMessage = errorMessage ,
      onTextChanged = onDateChanged,
      rightIconRes = R.drawable.ic_calendar_outlined
  )
}