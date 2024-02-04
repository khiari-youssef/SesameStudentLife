import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import tn.sesame.designsystem.SesameFontFamilies

@Composable
fun SesameCheckableSettingsOption(
    modifier: Modifier = Modifier,
    label : String,
    isSelected : Boolean,
    isEnabled : Boolean = true,
    onItemSelectedStateChanged : (isSelected : Boolean)->Unit
) {
    ConstraintLayout(
        modifier = modifier
            .alpha(if (isEnabled) 1f else 0.4f)
            .padding(8.dp)
    ) {
        val (nameRef,checkboxRef) = createRefs()
        Text(
            modifier = Modifier.constrainAs(nameRef) {
                start.linkTo(parent.start,8.dp)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(checkboxRef.start, 16.dp)
                width = Dimension.fillToConstraints
            },
            text = label,
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = SesameFontFamilies.MainMediumFontFamily,
                fontWeight = FontWeight(500),
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Start
            )
        )
        Checkbox(
            modifier = Modifier.constrainAs(checkboxRef) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end, 12.dp)
            },
            checked = isSelected,
            enabled = isEnabled,
            onCheckedChange = onItemSelectedStateChanged,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.secondary
            )
        )
    }

}