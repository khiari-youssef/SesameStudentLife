package tn.sesame.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProjectCard(
    modifier: Modifier = Modifier,
    content : @Composable ColumnScope.()->Unit
) {
  Card(
      modifier = modifier,
      colors = CardDefaults.cardColors(
          containerColor = MaterialTheme.colorScheme.surfaceVariant
      ),
      shape = MaterialTheme.shapes.medium,
      content = content
  )
}

@Composable
fun HumanResourceCard() {

}

@Composable
fun ProjectTechnologyCard() {

}