import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import tn.sesame.designsystem.AliceBlue
import tn.sesame.designsystem.Charcoal2
import tn.sesame.designsystem.DuskBlue
import tn.sesame.designsystem.LightGreyBlue
import tn.sesame.designsystem.NiceBlue

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SesameDateRangePicker(
    modifier: Modifier = Modifier,
    dateValidator : (Long)->Boolean={true}
) {

 val colors = DatePickerDefaults.colors(
   containerColor = AliceBlue,
     todayContentColor = NiceBlue,
     todayDateBorderColor = NiceBlue,
     selectedDayContainerColor = NiceBlue,
     selectedDayContentColor = if (isSystemInDarkTheme()) Color(0xFFB6B6B6)
     else Color.White,
     selectedYearContainerColor = AliceBlue,
     dayInSelectionRangeContainerColor = if (isSystemInDarkTheme()) LightGreyBlue else DuskBlue,
     dayInSelectionRangeContentColor = Charcoal2,
     dayContentColor = MaterialTheme.colorScheme.onBackground
 )
val state = rememberDateRangePickerState(
    yearRange = 1990..2050,
    initialSelectedStartDateMillis = null,
    initialSelectedEndDateMillis = null
)
DateRangePicker(
    modifier = modifier,
    state = state,
    dateValidator = dateValidator,
    colors = colors
)
}