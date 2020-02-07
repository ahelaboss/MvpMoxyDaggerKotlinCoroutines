package com.yourgains.mvpmoxydaggertemplate.common

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private const val FORMAT_PATTERN_SERVER = "yyyy-MM-dd'T'HH:mm:ssZ"
    private const val FORMAT_PATTERN_UI = "yyyy/MM/dd"
    private const val FORMAT_PATTERN_UI_SHORT = "yy/MM/dd"

    fun convertTimestampToStringDate(timeStamp: Long): String {
        val dateFormat = SimpleDateFormat(FORMAT_PATTERN_SERVER, Locale.getDefault())
        return dateFormat.format(Date(timeStamp))
    }

    fun convertServerFormatDateToUIStringDate(serverDate: String, isShot: Boolean = false): String {
        val sdf = SimpleDateFormat(FORMAT_PATTERN_SERVER, Locale.getDefault())
        val pattern = takeIf { isShot }?.let { FORMAT_PATTERN_UI_SHORT } ?: FORMAT_PATTERN_UI
        val output = SimpleDateFormat(pattern, Locale.getDefault())
        val d = sdf.parse(serverDate)
        return output.format(d)
    }

    fun convertStringStringToTimestamp(stringDate: String?): Long {
        if (!stringDate.isNullOrEmpty()) {
            val dateFormat = SimpleDateFormat(FORMAT_PATTERN_SERVER, Locale.getDefault())
            return dateFormat.parse(stringDate).time
        }
        return Date().time
    }
}