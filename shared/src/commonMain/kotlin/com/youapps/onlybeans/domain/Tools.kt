package com.youapps.onlybeans.domain

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.number

fun LocalDate.formatDMY() : String {
    val displayDay = if (day > 9) "$day" else "0$day"
    val displayMonth = if (month.number > 9) "${month.number}" else "0${month.number}"
    return "$displayDay/$displayMonth/$year"
}

fun LocalTime.formatHHMM() : String {
    val minutes = if (minute == 0) "00" else if (minute > 9) "$minute" else "0$minute"
    return "${hour}h${minutes}m"
}