package com.youapps.onlybeans.utilities
import android.util.Log

actual object KMPLogger {


     actual fun e(tag: String, message: String?, throwable: Throwable?) {
             if (throwable != null) {
                 Log.e(tag, message ?: "null", throwable)
             } else {
                 Log.e(tag, message ?: "null")
             }
     }

     actual fun d(tag: String, message: String?) {
             Log.d(tag, message ?: "null")
     }

     actual fun i(tag: String, message: String?) {
          Log.i(tag, message ?: "null")
     }
}