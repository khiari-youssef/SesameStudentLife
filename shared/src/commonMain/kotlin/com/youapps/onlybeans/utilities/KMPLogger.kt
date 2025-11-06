package com.youapps.onlybeans.utilities

expect object KMPLogger {

     fun e(tag: String, message: String?, throwable: Throwable?)

     fun d(tag: String, message: String?)

     fun i(tag: String, message: String?)
}