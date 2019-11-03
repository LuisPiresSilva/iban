package net.luispiressilva.iban.utils.helper

import android.content.Context
import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class TimeUtils {
    companion object {
        fun getUTCFormatter(): SimpleDateFormat {
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            formatter.timeZone = TimeZone.getTimeZone("UTC")

            return formatter
        }

        fun getUserDateFormatter(): SimpleDateFormat {
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            formatter.timeZone = TimeZone.getTimeZone("UTC")

            return formatter
        }

        fun secondsToString(pTime : Long, lowercase : Boolean = false) : String {
            val date = Date(0,0,0,0,0,pTime.toInt())
            var text = ""
            var formatter = SimpleDateFormat("s")
            if(pTime < 60) {
                text = " SEC"
            } else if (pTime < 3600){
                text = " MIN"
                if(pTime % 60 > 0){ //if secs (secs after taking out mins) > 0
                    formatter = SimpleDateFormat("m:ss")
                } else {
                    formatter = SimpleDateFormat("m")
                }

            } else {
                text = " HOUR"
                if(pTime % 3600 > 0){ //if secs (secs after taking out mins) > 0
                    formatter = SimpleDateFormat("h:m:ss")
                }
            }
            if(lowercase) {
                text = text.toLowerCase()
            }
            return formatter.format(date) + text
        }



        fun getTimeOfDayFormat(ctx : Context, hour : Int) : String {
            return if(!DateFormat.is24HourFormat(ctx)){
                if(hour >= 12) {
                    "p.m"
                } else {
                    "a.m"
                }
            } else {
                ""
            }
        }
    }
}

fun Int.timeFormatHour(ctx : Context) : Int {
    return if(!DateFormat.is24HourFormat(ctx)){
        if(this >= 12) {
            this - 12
        } else {
            return this
        }
    } else {
        this
    }
}