package com.ocse.baseandroid.utils

import android.annotation.SuppressLint
import android.content.Context
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 */
@SuppressLint("SimpleDateFormat")
object DateUtils {
    /**
     * 缺省的日期显示格式： yyyy-MM-dd
     */
    const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd"
    const val DEFAULT_DATE_FORMAT_POINT = "yyyy.MM.dd"

    /**
     * 缺省的日期时间显示格式：yyyy-MM-dd HH:mm:ss
     */
    const val DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
    const val DEFAULT_COMMON_DATETIME_FORMAT = "yyyy-MM-dd HH:mm"
    const val DEFAULT_DATE_YY_MM_DD = "MM/dd HH:mm"

    private val sFormatMessageToday = SimpleDateFormat("今天")
    private val sFormatToday = SimpleDateFormat("今天 HH:mm")
    private val sFormatHourMinute = SimpleDateFormat("HH:mm")
    private val sFormatThisYear =
        SimpleDateFormat("MM/dd HH:mm")
    private val sFormatOtherYear =
        SimpleDateFormat("yy/MM/dd HH:mm")
    private val sFormatMessageThisYear =
        SimpleDateFormat("MM/dd")
    private val sFormatMessageOtherYear =
        SimpleDateFormat("yy/MM/dd")
    private val sFormatThisYearNoHour =
        SimpleDateFormat("MM/dd")
    private val sFormatOtherYearNoHour =
        SimpleDateFormat("yy/MM/dd")
    private val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    @Throws(ParseException::class)
    fun formatDate(date: Date): String {
        synchronized(
            sdf
        ) { return sdf.format(date) }
    }

    @Throws(ParseException::class)
    fun parse(strDate: String): Date? {
        synchronized(
            sdf
        ) { return sdf.parse(strDate) }
    }

    @Throws(ParseException::class)
    fun formatTodayDate(date: Date): String {
        synchronized(
            sFormatToday
        ) { return sFormatToday.format(date) }
    }

    @Throws(ParseException::class)
    fun parseToday(strDate: String): Date? {
        synchronized(
            sFormatToday
        ) { return sFormatToday.parse(strDate) }
    }

    @Throws(ParseException::class)
    fun formatThisYearDate(date: Date): String {
        synchronized(
            sFormatThisYear
        ) { return sFormatThisYear.format(date) }
    }

    @Throws(ParseException::class)
    fun parseThisYear(strDate: String): Date ?{
        synchronized(
            sFormatThisYear
        ) { return sFormatThisYear.parse(strDate) }
    }

    @Throws(ParseException::class)
    fun formatOtherYearDate(date: Date): String {
        synchronized(
            sFormatOtherYear
        ) { return sFormatOtherYear.format(date) }
    }

    @Throws(ParseException::class)
    fun parseOtherYear(strDate: String): Date? {
        synchronized(
            sFormatOtherYear
        ) { return sFormatOtherYear.parse(strDate) }
    }

    @Throws(ParseException::class)
    fun formatMessageToday(date: Date): String {
        synchronized(
            sFormatMessageToday
        ) { return sFormatMessageToday.format(date) }
    }

    @Throws(ParseException::class)
    fun parseMessageToday(strDate: String): Date? {
        synchronized(
            sFormatMessageToday
        ) { return sFormatMessageToday.parse(strDate) }
    }

    @Throws(ParseException::class)
    fun formatMessageThisYear(date: Date): String {
        synchronized(
            sFormatMessageThisYear
        ) { return sFormatMessageThisYear.format(date) }
    }

    @Throws(ParseException::class)
    fun parseMessageThisYear(strDate: String): Date? {
        synchronized(
            sFormatMessageThisYear
        ) { return sFormatMessageThisYear.parse(strDate) }
    }

    @Throws(ParseException::class)
    fun formatMessageOtherYear(date: Date): String {
        synchronized(
            sFormatMessageOtherYear
        ) { return sFormatMessageOtherYear.format(date) }
    }

    @Throws(ParseException::class)
    fun parseMessageOtherYear(strDate: String): Date ?{
        synchronized(
            sFormatMessageOtherYear
        ) { return sFormatMessageOtherYear.parse(strDate) }
    }

    /**
     * 获取当前年份
     *
     * @return
     */
    val year: Int
        get() {
            val c = Calendar.getInstance()
            return c[Calendar.YEAR]
        }

    /*时间戳转换成字符窜*/
    fun getDateToString(time: Long): String {
        val d = Date(time)
        val format = SimpleDateFormat(
            DEFAULT_DATETIME_FORMAT,
            Locale.getDefault()
        )
        return format.format(d)
    }

    /**
     * 转换时间为yyyy-MM-dd HH:mm格式
     *
     * @param time
     * @return
     */
    fun getDateToRomanString(time: Long): String {
        val d = Date(time)
        val format = SimpleDateFormat(
            DEFAULT_COMMON_DATETIME_FORMAT,
            Locale.getDefault()
        )
        return format.format(d)
    }

    /*时间戳转换成字符窜*/
    fun getActDateToString(time: Long): String {
        val d = Date(time)
        return SimpleDateFormat(
            DEFAULT_DATE_YY_MM_DD,
            Locale.getDefault()
        ).format(d)
    }

    fun getTimeWithoutHour(date: Date): String {
        val format = SimpleDateFormat(
            DEFAULT_DATE_FORMAT,
            Locale.getDefault()
        )
        return format.format(date)
    }

    /**
     * YY-MM-dd
     *
     * @param timeMill 毫秒单位
     * @return
     */
    fun getDateFromStamp(timeMill: Long): String {
        val d = Date(timeMill)
        val format = SimpleDateFormat(
            DEFAULT_DATE_FORMAT,
            Locale.getDefault()
        )
        return format.format(d)
    }

    /**
     * 日期字符串转时间戳
     *
     * @param dateStr
     * @return
     * @throws ParseException
     */
    fun dateToStamp(dateStr: String): Long {
        val format = SimpleDateFormat(
            DEFAULT_DATE_FORMAT,
            Locale.getDefault()
        )
        try {
            return format.parse(dateStr).time
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return Date().time // 默认当天
    }

    /**
     * YY-MM-dd
     *
     * @param time 秒单位
     * @return
     */
    private fun getDateWithoutHourByPoint(time: Long): String {
        val timeMill = time * 1000 // 转成毫秒
        val d = Date(timeMill)
        val format = SimpleDateFormat(
            DEFAULT_DATE_FORMAT_POINT,
            Locale.getDefault()
        )
        return format.format(d)
    }

    /**
     * 拼接起始和结束时间
     *
     * @param startTime
     * @param endTime
     * @return
     */
    fun getStartAndEndTimeRang(startTime: Long, endTime: Long): String {
        val startStr =
            getDateWithoutHourByPoint(startTime)
        val endStr =
            getDateWithoutHourByPoint(endTime)
        return "$startStr-$endStr"
    }

    fun dayToNow(time: Long, context: Context?): String {
        return dayToNow(time, true, context)
    }

    fun dayToTime(time: Long, context: Context?): String {
        return dayToTime(time, true, context)
    }

    private fun dayToNow(
        time: Long,
        displayHour: Boolean,
        context: Context?
    ): String {
        val timeMill = time * 1000
        val nowMill = System.currentTimeMillis()
        val minute = (nowMill - timeMill) / 60000
        if (minute < 60) {
            if (minute <= 0) {
                val second = ((nowMill - timeMill) / 1000).coerceAtLeast(1).toInt()
                // 由于手机时间的原因，有时候会为负，这时候显示1秒前
                return second.toString() + "秒前"
            } else {
                return minute.toString() + "分钟前"
            }
        }
        val calendar = GregorianCalendar()
        calendar.timeInMillis = timeMill
        val year = calendar[GregorianCalendar.YEAR]
        val month = calendar[GregorianCalendar.MONTH]
        val day = calendar[GregorianCalendar.DAY_OF_MONTH]
        calendar.timeInMillis = nowMill
        return if (calendar[GregorianCalendar.YEAR] != year) { // 不是今年
            val formatOtherYear =
                if (displayHour) sFormatOtherYear else sFormatMessageOtherYear
            formatOtherYear.timeZone = TimeZone.getTimeZone("GMT+08:00")
            formatOtherYear.format(timeMill)
        } else if (calendar[GregorianCalendar.MONTH] != month
            || calendar[GregorianCalendar.DAY_OF_MONTH] != day
        ) { // 今年
            val formatThisYear =
                if (displayHour) sFormatThisYear else sFormatMessageThisYear
            formatThisYear.timeZone = TimeZone.getTimeZone("GMT+08:00")
            formatThisYear.format(timeMill)
        } else { // 今天
            val formatToday =
                sFormatHourMinute
            formatToday.timeZone = TimeZone.getTimeZone("GMT+08:00")
            formatToday.format(timeMill)
        }
    }

    private fun dayToTime(
        time: Long,
        displayHour: Boolean,
        context: Context?
    ): String {
        val timeMill = time * 1000
        val nowMill = System.currentTimeMillis()
        val minute = (nowMill - timeMill) / 60000
        if (minute < 60) {
            if (minute <= 0) {
                val second = ((nowMill - timeMill) / 1000).coerceAtLeast(1).toInt()
                // 由于手机时间的原因，有时候会为负，这时候显示1秒前
                return second.toString() + "秒前"
            } else {
                return minute.toString() + "分钟前"
            }
        }
        val calendar = GregorianCalendar()
        calendar.timeInMillis = timeMill
        val year = calendar[GregorianCalendar.YEAR]
        val month = calendar[GregorianCalendar.MONTH]
        val day = calendar[GregorianCalendar.DAY_OF_MONTH]
        calendar.timeInMillis = nowMill
        return if (calendar[GregorianCalendar.YEAR] != year) { // 不是今年
            val formatOtherYear =
                if (displayHour) sFormatOtherYearNoHour else sFormatMessageOtherYear
            formatOtherYear.timeZone = TimeZone.getTimeZone("GMT+08:00")
            formatOtherYear.format(timeMill)
        } else if (calendar[GregorianCalendar.MONTH] != month
            || calendar[GregorianCalendar.DAY_OF_MONTH] != day
        ) { // 今年
            val formatThisYear =
                if (displayHour) sFormatThisYearNoHour else sFormatMessageThisYear
            formatThisYear.timeZone = TimeZone.getTimeZone("GMT+08:00")
            formatThisYear.format(timeMill)
        } else { // 今天
            val formatToday =
                if (displayHour) sFormatToday else sFormatMessageToday
            formatToday.timeZone = TimeZone.getTimeZone("GMT+08:00")
            formatToday.format(timeMill)
        }
    }

    /**
     * 返回过去一天/一周/一月/一年的起始事件
     *
     * @param timeMarking 时间标识：-1 昨天；1 当天；2 一周；3 一月；4 一年
     * @return time
     */
    fun getTargetNodeZeroStamp(timeMarking: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0
        if (timeMarking == -1) {
            calendar.add(Calendar.DATE, -1) // 昨天
            return calendar.timeInMillis
        }
        if (timeMarking == 1) {
            calendar.add(Calendar.DATE, 0) // 当天
        } else if (timeMarking == 2) {
            if (calendar[Calendar.DAY_OF_WEEK] == 1) {
                calendar.add(Calendar.DAY_OF_MONTH, -1)
            }
            calendar[Calendar.DAY_OF_WEEK] = Calendar.MONDAY
        } else if (timeMarking == 3) {
            calendar[Calendar.DAY_OF_MONTH] = 1 // 本月
        } else if (timeMarking == 4) { // 本年度
            calendar[Calendar.DATE] = 1
            calendar[Calendar.MONTH] = Calendar.JANUARY
        }
        return calendar.timeInMillis
    }

    /**
     * 获取昨天23点59分59秒
     *
     * @return
     */
    val yesterdayEndStamp: Long
        get() {
            val calendar = Calendar.getInstance()
            calendar.time = Date()
            calendar[Calendar.HOUR_OF_DAY] = 0
            calendar[Calendar.MINUTE] = 0
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = -1
            return calendar.timeInMillis
        }

    /**
     * 是否在指定指定时间内,24小时制
     *
     * @return
     */
    fun inLimitedTime(startHour: Int, endHour: Int): Boolean {
        val hh =
            SimpleDateFormat("HH", Locale.CHINA)
        val mm =
            SimpleDateFormat("mm", Locale.CHINA)
        // 获取指定时区的时间
        hh.timeZone = TimeZone.getTimeZone("GMT+08")
        mm.timeZone = TimeZone.getTimeZone("GMT+08")
        val date = Date()
        val hour = hh.format(date)
        val minute = mm.format(date)
        val start = startHour * 60
        val end = endHour * 60
        val minuteOfDay = hour.toInt() * 60 + minute.toInt()
        MyLog.e("stf--hour:minute-->$hour:$minute")
        return minuteOfDay >= start && minuteOfDay <= end
    }
}