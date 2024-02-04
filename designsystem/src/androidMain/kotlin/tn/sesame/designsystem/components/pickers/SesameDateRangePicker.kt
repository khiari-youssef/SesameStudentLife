import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import tn.sesame.designsystem.AliceBlue
import tn.sesame.designsystem.DuskBlue
import tn.sesame.designsystem.LightGreyBlue
import tn.sesame.designsystem.NiceBlue
import java.time.LocalDateTime

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
     dayInSelectionRangeContentColor = MaterialTheme.colorScheme.surfaceVariant,
     dayContentColor = MaterialTheme.colorScheme.onBackground
 )
val currentYear = LocalDateTime.now().year
val state = rememberDateRangePickerState(
    yearRange = currentYear-1..currentYear+1,
    initialSelectedStartDateMillis = null,
    initialSelectedEndDateMillis = null
)
Box(
    modifier = modifier
        .fillMaxSize(),
    contentAlignment = Alignment.TopCenter
){
    DateRangePicker(
        modifier = Modifier
            .align(Alignment.TopCenter)
            .fillMaxSize(),
        state = state,
        dateValidator = dateValidator,
        colors = colors
    )
    
}
}