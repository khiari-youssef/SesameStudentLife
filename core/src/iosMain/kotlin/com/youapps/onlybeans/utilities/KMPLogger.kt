package com.youapps.onlybeans.utilities

import platform.Foundation.NSLog

actual object KMPLogger {

     actual fun e(tag: String, message: String?, throwable: Throwable?) {
          if (throwable != null) {
               NSLog("ERROR: [$tag] ${message ?: "null"}. Throwable: $throwable CAUSE ${throwable.cause}")
          } else {
               NSLog("ERROR: [$tag] ${message ?: "null"}")
          }
     }

     actual fun d(tag: String, message: String?) {
          NSLog("DEBUG: [$tag] ${message ?: "null"}")

     }

     actual fun i(tag: String, message: String?) {
          NSLog("INFO: [$tag] ${message ?: "null"}")
     }
}