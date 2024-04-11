package tn.sesame.android_services.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import tn.sesame.android_services.services.AlarmSchedulingService.Companion.AlarmScheduleRequestCode

interface AlarmSchedulingService{

    companion object{
        const val AlarmScheduleRequestCode : Int = 0xFF24
    }
    fun setupAlarm(timeInMS : Long)
}

internal class AlarmSchedulingServiceImpl(
    private val context : Context
) : AlarmSchedulingService{
    private val alarmManager : AlarmManager= context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun setupAlarm(timeInMS : Long) {
        val intent = Intent(context, CustomAlarmReceiver::class.java).apply {
            putExtra("message","alarm test")
        }
        val pendingIntent =
            PendingIntent.getBroadcast(context, AlarmScheduleRequestCode, intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC,
            SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_HALF_HOUR,
            AlarmManager.INTERVAL_HALF_HOUR,
            pendingIntent
        )
    }
}