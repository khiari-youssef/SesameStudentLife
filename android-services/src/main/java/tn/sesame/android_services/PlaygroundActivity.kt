package tn.sesame.android_services

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.RESULT_FORMAT_JPEG
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.RESULT_FORMAT_PDF
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.SCANNER_MODE_FULL
import com.google.mlkit.vision.documentscanner.GmsDocumentScanning
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult
import tn.sesame.android_services.ui.DocumentFile
import tn.sesame.android_services.ui.DocumentScanner
import tn.sesame.android_services.ui.Documents
import tn.sesame.designsystem.SesameTheme

class PlaygroundActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SesameTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val rootNavController = rememberNavController()
                    NavHost(
                        route  = "Main",
                        navController = rootNavController,
                        startDestination = "DocumentScanner"
                    ) {
                        composable(
                            route = "DocumentScanner"
                        ) {
                            val currentlySelectedDocument : MutableState<Documents> = remember { mutableStateOf(
                                Documents.EMPTY
                            ) }
                            val scanActivityLauncher : ActivityResultLauncher<IntentSenderRequest> = rememberLauncherForActivityResult(
                                ActivityResultContracts.StartIntentSenderForResult()
                            ){ result->
                             if(result.resultCode == RESULT_OK) {
                                 val resultData = GmsDocumentScanningResult.fromActivityResultIntent(result.data)
                                 currentlySelectedDocument.value = resultData?.pages?.filterNotNull()?.map {
                                     it.imageUri
                                 }?.let { docs->
                                     Documents(
                                         data = docs.map {
                                             DocumentFile(it)
                                         }
                                     )
                                 } ?: Documents.EMPTY
                             }
                            }
                            val options = GmsDocumentScannerOptions.Builder()
                                .setGalleryImportAllowed(false)
                                .setPageLimit(2)
                                .setResultFormats(RESULT_FORMAT_JPEG /*, RESULT_FORMAT_PDF*/)
                                .setScannerMode(SCANNER_MODE_FULL)
                                .build()
                            val scanner = GmsDocumentScanning.getClient(options)

                            DocumentScanner(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .safeContentPadding()
                                    .padding(
                                        top = 32.dp
                                    ),
                                selectedDocument = currentlySelectedDocument.value,
                                onDocumentScan = {
                                    scanner.getStartScanIntent(this@PlaygroundActivity)
                                        .addOnSuccessListener { intentSender ->
                                            scanActivityLauncher.launch(IntentSenderRequest.Builder(intentSender).build())
                                        }
                                        .addOnFailureListener {

                                        }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

