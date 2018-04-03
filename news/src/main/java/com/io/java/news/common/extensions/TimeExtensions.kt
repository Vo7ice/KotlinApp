package com.io.java.news.common.extensions

import android.content.Context
import com.io.java.news.R
import java.util.*

/**
 * Created by huguojin on 2018/4/3.
 */
fun Long.getHumanRead(context: Context): String {
    val dateTime = Date(this * 1000)
    val current = Calendar.getInstance().time
    val timeInternal = ((current.time - dateTime.time) / 1000).toInt()
    var temp: Int
    val result: String
    if (timeInternal < 60) {
        result = context.getString(R.string.txt_time_recently)
    } else {
        temp = timeInternal / 60
        if (temp < 60) {
            result = context.resources.getQuantityString(R.plurals.txt_time_about_minute, temp, temp)
        } else {
            temp /= 60
            if (temp < 24) {
                result = context.resources.getQuantityString(R.plurals.txt_time_about_hour, temp, temp)
            } else {
                temp /= 24
                if (temp < 30) {
                    result = context.resources.getQuantityString(R.plurals.txt_time_about_day, temp, temp)
                } else {
                    temp /= 30
                    if (temp < 12) {
                        result = context.resources.getQuantityString(R.plurals.txt_time_about_month, temp, temp)
                    } else {
                        temp /= 12
                        result = context.resources.getQuantityString(R.plurals.txt_time_about_year, temp, temp)
                    }
                }
            }
        }
    }
    return result
}