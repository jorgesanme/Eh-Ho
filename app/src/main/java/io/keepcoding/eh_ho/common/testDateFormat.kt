package io.keepcoding.eh_ho.common

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.util.Log
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Month
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

@SuppressLint("NewApi")
fun main(args: Array<String>) {

//    val current = LocalDateTime.now()
//
//    val formatter = DateTimeFormatter.ofPattern("MM-dd")
//    val formatted = current.format(formatter)
//    val date = LocalDate.parse(current.toString(), formatter)
//
//    println("Current Date and Time is: $formatted")
//    println("Current Date otro Time is: $date")

    // Date and time
    val testDate = "2021-05-19T10:45:58.919Z"
    val sinZ = testDate.replace("Z", "0")
    val dateTime = LocalDateTime.of(2016, Month.APRIL, 15, 3, 15)
    println(dateTime)

// Date only
    val date = LocalDate.of(2016, Month.APRIL, 15)
    println(date)

// Time only
    val time = LocalTime.of(3, 15, 10)
    println(time)
    val midate = LocalDateTime.now()
    val eldate = LocalDateTime
        .parse(sinZ)
        .toLocalDate().format(
            DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
                .withLocale(Locale("es", "ES")))
    println(eldate)



}
