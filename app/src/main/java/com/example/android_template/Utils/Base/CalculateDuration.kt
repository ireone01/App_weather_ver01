package com.example.android_template.Utils.Base

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class calculateDuration {

    @RequiresApi(Build.VERSION_CODES.O)
    companion object {
        fun calculateHour(SunorMoon : String,startTime: String, endTime: String): String {
            val format = DateTimeFormatter.ofPattern("HH:mm:ss")
            val start = LocalTime.parse(startTime, format)
            val end = LocalTime.parse(endTime, format)
            if(SunorMoon =="Sun" || (SunorMoon=="Moon" && !start.isBefore(LocalTime.MIDNIGHT))) {
                val hour = ChronoUnit.HOURS.between(start, end)
                val minut = ChronoUnit.MINUTES.between(start, end) % 60
                return "${hour}h : ${minut}m"
            }else{
                var hour =24+ ChronoUnit.HOURS.between(start,LocalTime.MIDNIGHT)
                var hour1 = ChronoUnit.HOURS.between(LocalTime.MIDNIGHT,end)

                var minut =24*60+ ChronoUnit.MINUTES.between(start,LocalTime.MIDNIGHT)
                var minut1 = ChronoUnit.MINUTES.between(LocalTime.MIDNIGHT,end)
                if((minut % 60).toFloat() != 0.toFloat()){
                    hour--
                }
                val totalHour = hour+hour1
                val totalMinut = (minut+minut1)%60
                return "${totalHour}h : ${totalMinut}m"

            }
        }

        fun extractTime(dateTime: String): String {
            return dateTime.substring(11, 19)
        }

        fun extractDay(dateTime: String): String{
            return dateTime.substring(5,10)
        }

        fun fahrenheitToCelsius(fahrenheit: Double): String {
            val x = (fahrenheit - 32) * 5 / 9
            return String.format("%.1f",x)
        }
    }
}
