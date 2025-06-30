package tn.sesame.android_services.services.notificationService

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Build.VERSION_CODES
import androidx.core.app.NotificationCompat
import tn.sesame.android_services.services.notificationService.SesameNotificationGroup.Companion.SESAME_SESSIONS_GROUP

data class SesameNotificationGroup(
    val id : String,
    val name : String
){
    companion object{
        const val SESAME_SESSIONS_GROUP : String = "sesame_sessions_group"
    }
}

data class SesameNotificationChannel(
    val id : String,
    val name : String,
    val description : String,
    val importance : Int = NotificationManager.IMPORTANCE_DEFAULT,
    val canShowBadge : Boolean = false,
    val enableVibration : Boolean = true
){
    companion object{
    const val SESAME_NOTIFICATION_CHANNEL : String = "SESAME_NOTIFICATION_CHANNEL"
    }
}

data class SesameNotification(
    val id : Int = System.currentTimeMillis().toInt(),
    val channelID : String = SesameNotificationChannel.SESAME_NOTIFICATION_CHANNEL,
    val title : String,
    val shortDescription : String,
    val longDescription : String?=null,
    val priority: Int = NotificationCompat.PRIORITY_DEFAULT,
    val smallIcon : Int,
    val largeIcon : Icon?=null
)


class CustomNotificationService(
    private val context: Context
) {
    private val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

    fun setupChannel(channelConfig : SesameNotificationChannel,notificationGroups : List<SesameNotificationGroup> = listOf()){
        context.run {
            val mChannel = NotificationChannel(channelConfig.id, channelConfig.name, channelConfig.importance)
            mChannel.description = channelConfig.description
            mChannel.group = SESAME_SESSIONS_GROUP
            if (channelConfig.canShowBadge){
                mChannel.canShowBadge()
            }
            mChannel.enableVibration(channelConfig.enableVibration)
            notificationGroups.forEach { group->
                val alreadyExists = notificationManager.notificationChannelGroups.any {
                    it.id == group.id
                }
                if (alreadyExists.not()){
                    notificationManager.createNotificationChannelGroup(
                        NotificationChannelGroup(group.id, group.name)
                    )
                }
            }
            notificationManager.createNotificationChannel(mChannel)
        }
    }


    fun showNotification(notification : SesameNotification,intent: Intent){
        if (Build.VERSION.SDK_INT >= VERSION_CODES.TIRAMISU){
            if (context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) return
        }
        val builder = NotificationCompat.Builder(context, notification.channelID)
            .setSmallIcon(notification.smallIcon)
            .setLargeIcon(notification.largeIcon)
            .setContentTitle(notification.title)
            .setContentText(notification.shortDescription)
            .setPriority(notification.priority)
            .setAutoCancel(true)
        notification.longDescription?.ifBlank { null }?.run {
            builder.setStyle(NotificationCompat.BigTextStyle()
                .bigText(this))
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        builder.setContentIntent(pendingIntent)
        notificationManager.notify(notification.id, builder.build())
    }
}