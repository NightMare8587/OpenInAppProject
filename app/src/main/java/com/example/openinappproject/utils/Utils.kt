package com.example.openinappproject.utils

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

fun getCurrentTimeOfDay(): String {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)

    return when (hour) {
        in 0..5 -> "Night"
        in 6..11 -> "Morning"
        in 12..17 -> "Afternoon"
        in 18..21 -> "Evening"
        else -> "Night"
    }
}

fun formatTimestampToIST(timestamp: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
    val zonedDateTime = ZonedDateTime.parse(timestamp, formatter)
    val istZoneId = ZoneId.of("Asia/Kolkata")
    val istZonedDateTime = zonedDateTime.withZoneSameInstant(istZoneId)
    val outputFormatter = DateTimeFormatter.ofPattern("dd MMM, yyyy")
    return istZonedDateTime.format(outputFormatter)
}