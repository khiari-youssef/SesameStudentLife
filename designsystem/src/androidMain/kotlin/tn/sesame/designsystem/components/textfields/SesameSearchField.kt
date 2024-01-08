package tn.sesame.designsystem.components.textfields

import PlaceholderText
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import tn.sesame.designsystem.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SesameSearchField(
    modifier: Modifier = Modifier,
    placeholderRes: Int,
    query : String = "",
    onSearchQueryChanged : (query : String)->Unit
) {
    SearchBar(
        modifier = modifier,
        placeholder = {
            PlaceholderText(
                text = stringResource(id = placeholderRes),
                fontSize = 14.sp
            )
        },
        query = query,
        onQueryChange = onSearchQueryChanged ,
        onSearch ={},
        leadingIcon = {
               Icon(
                   imageVector = ImageVector.vectorResource(R.drawable.ic_search),
                   contentDescription = "",
                   tint = MaterialTheme.colorScheme.secondary
               )
        },
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable {
                 onSearchQueryChanged("")
                },
                imageVector = ImageVector.vectorResource(R.drawable.ic_clear),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.secondary
            )
        },
        active = false,
        onActiveChange ={}
    ) {

    }
}