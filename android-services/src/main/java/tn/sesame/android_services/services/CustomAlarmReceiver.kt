package tn.sesame.android_services.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class CustomAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        Log.i("AlarmScheduling", "onNewIntent: ${p1?.extras?.getString("message")}")
    }
}