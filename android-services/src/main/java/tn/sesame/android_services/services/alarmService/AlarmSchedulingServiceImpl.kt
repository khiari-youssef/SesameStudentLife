package tn.sesame.android_services.services.alarmService

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import tn.sesame.android_services.services.alarmService.AlarmSchedulingService.Companion.AlarmScheduleRequestCode

interface AlarmSchedulingService{

    companion object{
        const val AlarmScheduleRequestCode : Int = 0xFF24
    }
    fun setupAlarm(timeInMS : Long,title : String,message : String,redirectionData : Map<String,String>?=null)
}

internal class AlarmSchedulingServiceImpl(
    private val context : Context
) : AlarmSchedulingService {
    private val alarmManager : AlarmManager= context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun setupAlarm(timeInMS : Long,title : String,message : String,redirectionData : Map<String,String>?) {
        val intent = Intent(context, CustomAlarmReceiver::class.java).apply {
            putExtra("title",title)
            putExtra("description",message)
            redirectionData?.forEach { (key, value) ->
                putExtra(key,value)
            }
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