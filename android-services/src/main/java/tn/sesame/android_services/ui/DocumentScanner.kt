package tn.sesame.android_services.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun DocumentScanner(
    modifier : Modifier,
    selectedDocument : Documents,
    onDocumentScan : ()->Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                24.dp,Alignment.CenterVertically
            )
        ) {
            Button(
                onClick = onDocumentScan,
                content = {
                    Text("Scan")
                }
            )
            when (selectedDocument.data.size) {
                0 -> Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "No documents scanned yet !",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        textAlign = TextAlign.Center
                    )
                )
                1 -> {
                    AsyncImage(
                        modifier = Modifier
                            .padding(
                                horizontal = 16.dp
                            )
                            .fillMaxWidth()
                            .aspectRatio(16/9f),
                        contentDescription = "Scanned document",
                        model = selectedDocument.data.first().uri
                    )
                }
                else -> {
                    LazyRow(
                        modifier = Modifier
                            .padding(
                                horizontal = 16.dp
                            )
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        items(selectedDocument.data) { document->
                            AsyncImage(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(16/9f),
                                contentDescription = "Scanned document",
                                model = document.uri
                            )
                        }
                    }
                }
            }
        }
    }
}