package com.pdstudios.sleeptrackerpractice3

import com.pdstudios.sleeptrackerpractice3.database.SleepNight
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.logging.SimpleFormatter
import kotlin.math.abs

fun formatNights(nights: List<SleepNight>): String {
    var formattedNights = ""
    nights.forEach { night ->
        formattedNights += formatNight(night)
    }
    return formattedNights
}

fun formatNight(night: SleepNight): String {
    var formattedNight = ""

    val dateTime = GetLocalDateTime(night)

    val formatDateTime = DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm:ss")
    val formatTime = DateTimeFormatter.ofPattern("HH:mm:ss")

    val startTime = dateTime.startTimeLDT.format(formatDateTime)
    val endTime = dateTime.endTimeLDT.format(formatDateTime)
    val duration = dateTime.duration.format(formatTime)
    val quality = night.quality.toString()

    formattedNight+= "NightID: ${night.nightId}\n"
    formattedNight+= "StartTime: $startTime\n"
    if (night.startTimeMillis != night.endTimeMillis) {
        formattedNight += "EndTime: $endTime\n"
        formattedNight += "Duration: $duration\n"
    }
    if (night.quality != -1) {
        formattedNight += "Quality: $quality/6\n"
    }
    formattedNight+= "~~~~~~~~~~~~~~~~~~~~~~~~~\n"

    return formattedNight
}

class GetLocalDateTime(night: SleepNight) {
    val startTimeLDT: LocalDateTime = Instant.ofEpochMilli(night.startTimeMillis).atZone(ZoneId.systemDefault()).toLocalDateTime()
    val endTimeLDT: LocalDateTime = Instant.ofEpochMilli(night.endTimeMillis).atZone(ZoneId.systemDefault()).toLocalDateTime()
    private val hour = abs(endTimeLDT.hour - startTimeLDT.hour)
    private val minute = abs(endTimeLDT.minute - startTimeLDT.minute)
    private val second = abs(endTimeLDT.second - startTimeLDT.second)
    val duration: LocalTime = LocalTime.of(hour, minute, second)
}