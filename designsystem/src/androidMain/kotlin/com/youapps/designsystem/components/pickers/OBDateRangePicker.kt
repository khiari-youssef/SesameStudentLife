import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.youapps.designsystem.Alabaster
import com.youapps.designsystem.Licorice
import com.youapps.designsystem.RoseEbony
import java.time.LocalDateTime

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OBDateRangePicker(
    modifier: Modifier = Modifier,
    dateValidator : (dateInMS : Long)->Boolean={ true},
    yearValidator : (year : Int)->Boolean={ true}
) {

 val colors = DatePickerDefaults.colors(
   containerColor = Alabaster,
     todayContentColor = RoseEbony,
     todayDateBorderColor = RoseEbony,
     selectedDayContainerColor = RoseEbony,
     selectedDayContentColor = if (isSystemInDarkTheme()) Color(0xFFB6B6B6)
     else Color.White,
     selectedYearContainerColor = Alabaster,
     dayInSelectionRangeContainerColor = Licorice,
     dayInSelectionRangeContentColor = MaterialTheme.colorScheme.surfaceVariant,
     dayContentColor = MaterialTheme.colorScheme.onBackground
 )
val currentYear = LocalDateTime.now().year
val state = rememberDateRangePickerState(
    yearRange = currentYear-1..currentYear+1,
    initialSelectedStartDateMillis = null,
    initialSelectedEndDateMillis = null,
    selectableDates = object: SelectableDates {

        override fun isSelectableDate(utcTimeMillis: Long) = dateValidator(utcTimeMillis)

        override fun isSelectableYear(year: Int) = yearValidator(year)
    }
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
        colors = colors
    )
    
}
}