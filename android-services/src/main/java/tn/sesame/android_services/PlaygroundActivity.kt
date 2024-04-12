package tn.sesame.android_services

import SesameButton
import android.Manifest
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.RESULT_FORMAT_JPEG
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.SCANNER_MODE_FULL
import com.google.mlkit.vision.documentscanner.GmsDocumentScanning
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult
import tn.sesame.android_services.services.alarmService.AlarmSchedulingService
import tn.sesame.android_services.services.alarmService.AlarmSchedulingServiceImpl
import tn.sesame.android_services.services.notificationService.CustomNotificationService
import tn.sesame.android_services.services.notificationService.SesameNotification
import tn.sesame.android_services.services.notificationService.SesameNotificationChannel
import tn.sesame.android_services.services.notificationService.SesameNotificationChannel.Companion.SESAME_NOTIFICATION_CHANNEL
import tn.sesame.android_services.services.notificationService.SesameNotificationGroup
import tn.sesame.android_services.ui.CameraView
import tn.sesame.android_services.ui.DocumentFile
import tn.sesame.android_services.ui.DocumentScanner
import tn.sesame.android_services.ui.Documents
import tn.sesame.android_services.ui.ImageDataExtraction
import tn.sesame.designsystem.SesameTheme

internal class PlaygroundActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val notificationManager = CustomNotificationService(this)
        notificationManager.setupChannel(
           channelConfig =  SesameNotificationChannel(
                id =SESAME_NOTIFICATION_CHANNEL,
                name ="Test Channel",
                description = "test description",
                importance = NotificationManager.IMPORTANCE_DEFAULT,
                canShowBadge = true,
                enableVibration = true
            ),
            notificationGroups = listOf(SesameNotificationGroup(
                SesameNotificationGroup.SESAME_SESSIONS_GROUP,
                "Test Group"
            ))
        )
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
                        startDestination = "menu"
                    ) {
                        composable("menu") {
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
                                    onClick = {
                                           rootNavController.navigate("DocumentScanner")
                                    },
                                    content = {
                                        Text("DocumentScanner")
                                    }
                                )
                                Button(
                                    onClick = {
                                        rootNavController.navigate("ImageLabeling")
                                    },
                                    content = {
                                        Text("ImageLabeling")
                                    }
                                )
                                Button(
                                    onClick = {
                                        rootNavController.navigate("QrCodeScanner")
                                    },
                                    content = {
                                        Text("QrCodeScanner")
                                    }
                                )
                                Button(
                                    onClick = {
                                        rootNavController.navigate("AlarmScheduler")
                                    },
                                    content = {
                                        Text("AlarmScheduler")
                                    }
                                )
                                Button(
                                    onClick = {
                                        rootNavController.navigate("Notifications")
                                    },
                                    content = {
                                        Text("Notifications")
                                    }
                                )
                            }
                        }
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
                        composable("ImageLabeling"){
                            ImageDataExtraction(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .safeContentPadding()
                                    .padding(
                                        top = 32.dp
                                    )
                            )
                        }
                        composable("QrCodeScanner"){
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                val cameraPermission = Manifest.permission.CAMERA
                                val localContext = LocalContext.current
                                val isCameraPermissionGranted : MutableState<Boolean> = remember {
                                    mutableStateOf(ContextCompat.checkSelfPermission(localContext,cameraPermission) == PackageManager.PERMISSION_GRANTED)
                                }
                                val cameraPermissionLauncher = rememberLauncherForActivityResult(
                                    contract = ActivityResultContracts.RequestPermission(),
                                    onResult = { isGranted->
                                        isCameraPermissionGranted.value = isGranted
                                    }
                                )
                                 LaunchedEffect(key1 = isCameraPermissionGranted.value) {
                                     if (isCameraPermissionGranted.value.not()){
                                         cameraPermissionLauncher.launch(cameraPermission)
                                     }
                                 }

                                if (isCameraPermissionGranted.value) {
                                    val qrCodePayload : MutableState<String?> = remember {
                                        mutableStateOf(null)
                                    }
                                    if (qrCodePayload.value == null) {
                                        Box(modifier =Modifier, contentAlignment = Alignment.Center ) {
                                            Text(
                                                modifier = Modifier
                                                    .align(Alignment.TopCenter)
                                                    .padding(
                                                        top = 20.dp
                                                    ),
                                                text = "Scan QR code",
                                                color = Color.White
                                            )
                                            CameraView(
                                                modifier = Modifier.fillMaxSize(),
                                                onQrCodeCaptured = {
                                                    qrCodePayload.value = it.ifBlank { null }
                                                }
                                            )
                                        }
                                    } else {
                                        Column(
                                            modifier = Modifier
                                                .padding(
                                                    horizontal = 20.dp,
                                                    vertical = 32.dp
                                                )
                                                .fillMaxWidth(),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.spacedBy(
                                                24.dp,Alignment.Top
                                            )
                                        ) {
                                            Text(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                text = "QR code scanned ! ",
                                                color = Color.White
                                            )
                                            Text(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                text = qrCodePayload.value ?: "",
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                            SesameButton(text ="Scan again" , variant = SesameButtonVariants.PrimaryHard) {
                                                qrCodePayload.value = null
                                            }
                                        }
                                    }

                                } else {
                                    Column(
                                        modifier = Modifier
                                            .padding(
                                                horizontal = 20.dp,
                                                vertical = 32.dp
                                            )
                                            .fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(
                                            24.dp,Alignment.Top
                                        )
                                    ) {
                                        Text(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            text = "Camera permission not granted",
                                            color = Color.White
                                        )
                                        SesameButton(text ="Grant camera permission" , variant = SesameButtonVariants.PrimaryHard) {
                                            cameraPermissionLauncher.launch(cameraPermission)
                                        }
                                    }
                                }
                            }
                        }
                        composable("AlarmScheduler"){
                            val localContext = LocalContext.current
                            val alarmSchedulingService : AlarmSchedulingService = AlarmSchedulingServiceImpl(localContext)
                            Box(
                                modifier = Modifier.fillMaxSize(),
                               contentAlignment = Alignment.Center
                            ) {
                                SesameButton(text ="Schedule alarm 1 min from now" , variant = SesameButtonVariants.PrimaryHard) {

                                    val time : Calendar = Calendar.getInstance().apply {
                                        timeInMillis = System.currentTimeMillis()
                                        add(Calendar.MINUTE, 1)
                                    }
                                    alarmSchedulingService.setupAlarm(
                                        timeInMS =time.timeInMillis,
                                        title = "Reminder : Big data course",
                                        message = "will start in 2 hours"
                                    )
                                }
                            }
                       }
                        composable("Notifications"){
                            val title = remember {
                                mutableStateOf("")
                            }
                            val description = remember {
                                mutableStateOf("")
                            }
                            val localContext = LocalContext.current
                            val notificationPermissionLauncher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.RequestPermission(),
                                onResult = { isGranted->

                                }
                            )
                            Column(
                                modifier = Modifier
                                    .padding(
                                        horizontal = 12.dp,
                                        vertical = 20.dp
                                    )
                                    .fillMaxSize(),
                               horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(
                                    20.dp,Alignment.CenterVertically
                                )
                            ) {
                                TextField(
                                    value = title.value,
                                    label = {
                                       Text(text = "Title")
                                    },
                                    onValueChange = {
                                        title.value = it
                                  })
                                TextField(
                                    value = description.value,
                                    label = {
                                        Text(text = "Description")
                                    },
                                    onValueChange = {
                                        description.value = it
                                    })
                                SesameButton(text ="Show notification" , variant = SesameButtonVariants.PrimaryHard) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                                        if (localContext.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED){
                                            notificationManager.showNotification(
                                                SesameNotification(
                                                    title = title.value,
                                                    shortDescription = description.value,
                                                    smallIcon = tn.sesame.designsystem.R.drawable.brand_logo
                                                ),
                                                Intent(localContext,PlaygroundActivity::class.java)
                                            )
                                        } else {
                                            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                        }
                                    } else {
                                        notificationManager.showNotification(
                                            SesameNotification(
                                                title = title.value,
                                                shortDescription = description.value,
                                                smallIcon = tn.sesame.designsystem.R.drawable.brand_logo
                                            ),
                                            Intent(localContext,PlaygroundActivity::class.java)
                                        )
                                    }

                                }
                            }
                       }
                    }
                }
            }
        }
    }

}

