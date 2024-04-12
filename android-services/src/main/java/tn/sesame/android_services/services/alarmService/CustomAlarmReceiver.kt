package tn.sesame.android_services.services.alarmService

import android.Manifest
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import tn.sesame.android_services.PlaygroundActivity
import tn.sesame.android_services.services.notificationService.CustomNotificationService
import tn.sesame.android_services.services.notificationService.SesameNotification
import tn.sesame.android_services.services.notificationService.SesameNotificationChannel
import tn.sesame.android_services.services.notificationService.SesameNotificationChannel.Companion.SESAME_NOTIFICATION_CHANNEL
import tn.sesame.android_services.services.notificationService.SesameNotificationGroup
import tn.sesame.designsystem.R

class CustomAlarmReceiver : BroadcastReceiver() {

    private var notificationManager : CustomNotificationService?=null


    override fun onReceive(context: Context?, p1: Intent?) {
        context?.run {
           if (notificationManager == null){
               notificationManager = CustomNotificationService(context)
           }
            Log.i("AlarmScheduling", "onNewIntent: ${p1?.extras?.getString("title")}")
            val title : String = p1?.extras?.getString("title") ?: "No title"
            val description : String =  p1?.extras?.getString("description") ?: "No description"
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                if (context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED){
                    notificationManager?.showNotification(
                        SesameNotification(
                            title = title,
                            shortDescription = description,
                            longDescription = description,
                            smallIcon = R.drawable.brand_logo
                        ),
                        Intent(context, PlaygroundActivity::class.java)
                    )
                } else {
                    Toast.makeText(context, "Enable notifications permission to get important reminders", Toast.LENGTH_SHORT).show()
                }
            } else {
                notificationManager?.showNotification(
                    SesameNotification(
                        title = title,
                        shortDescription = description,
                        smallIcon = R.drawable.brand_logo
                    ),
                    Intent(context, PlaygroundActivity::class.java)
                )
            }
        }

    }
}