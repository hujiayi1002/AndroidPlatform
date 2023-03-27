package com.ocse.baseandroid.utils

import android.text.TextUtils
import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class TimeUtil private constructor() {
    companion object {
        private val TAG = TimeUtil::class.java.javaClass.simpleName

        /* 默认的时间格式 */
        private const val DEF_FORMAT = "yyyy-MM-dd HH:mm:ss"
        /**
         * 将时间戳转为时间字符串
         *
         * @param millis 时间戳
         * @param format 时间格式
         * @return 时间字符串
         */
        /**
         * 将时间戳转为时间字符串
         *
         * @param millis 时间戳
         * @return 时间字符串
         */
        @JvmOverloads
        fun millis2String(
            millis: Long,
            format: String? = DEF_FORMAT
        ): String {
            val sdf = SimpleDateFormat(format)
            return sdf.format(Date(millis))
        }

        /**
         * 将时间字符串转换为时间戳
         *
         * @param timeStr 时间字符串
         * @return 毫秒时间戳
         */
      private  fun string2Millis(timeStr: String): Long {
            return if (TextUtils.isEmpty(timeStr)) 0 else string2Millis(
                timeStr,
                DEF_FORMAT
            )
        }

        /**
         * 将时间字符串转换为时间戳
         *
         * @param timeStr 时间字符串
         * @param format  时间格式
         * @return
         */
      private  fun string2Millis(timeStr: String, format: String?): Long {
            if (TextUtils.isEmpty(timeStr)) {
                return 0
            }
            var millis: Long = 0
            try {
                val sdf = SimpleDateFormat(format)
                val date = sdf.parse(timeStr)
                millis = date.time
            } catch (e: ParseException) {
                e.printStackTrace()
                MyLog.e(
                    TAG,
                    "String2Millis Error ========> timeStr: $timeStr"
                )
            }
            return millis
        }

        /**
         * 将时间字符串转为 Date 类型
         *
         * @param timeStr 时间字符串
         * @return Date 类型
         */
        fun string2Date(timeStr: String?): Date? {
            return if (TextUtils.isEmpty(timeStr)) null else string2Date(
                timeStr,
                DEF_FORMAT
            )
        }

        /**
         * 将时间字符串转为 Date 类型
         *
         * @param timeStr 时间字符串
         * @param format  时间格式
         * @return Date 类型
         */
        fun string2Date(timeStr: String?, format: String?): Date? {
            if (TextUtils.isEmpty(timeStr)) {
                return null
            }
            try {
                val sdf = SimpleDateFormat(format)
                return sdf.parse(timeStr)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return null
        }

        /**
         * 将 Date 类型转为时间字符串
         *
         * @param date Date 类型时间
         * @return 时间字符串
         */
        fun date2String(date: Date?): String? {
            return date?.let { date2String(it, DEF_FORMAT) }
        }

        /**
         * 将 Date 类型转为时间字符串
         *
         * 格式为 format
         *
         * @param date   Date 类型时间
         * @param format 时间格式
         * @return 时间字符串
         */
        fun date2String(date: Date?, format: String?): String {
            val sdf = SimpleDateFormat(format)
            return sdf.format(date)
        }

        /**
         * 将 Date 类型转为时间戳
         *
         * @param date Date 类型时间
         * @return 毫秒时间戳
         */
        fun date2Millis(date: Date?): Long? {
            return date?.time
        }

        /**
         * 将时间戳转为 Date 类型
         *
         * @param millis 毫秒时间戳
         * @return Date 类型时间
         */
        fun millis2Date(millis: Long): Date {
            return Date(millis)
        }
        /**
         * 将时间转换天数、小时数、分钟数、秒数
         *
         * @param timeStr 时间字符串
         * @param format  时间格式
         * @return int[0]: 天数 <br></br> int[1]: 小时数 <br></br> int[2]: 分钟数 <br></br> int[3]: 秒数
         */
        /**
         * 将时间转换天数、小时数、分钟数、秒数
         *
         * @param timeStr 时间字符串
         * @return int[0]: 天数 <br></br> int[1]: 小时数 <br></br> int[2]: 分钟数 <br></br> int[3]: 秒数
         */
        @JvmOverloads
        fun millis2Array(
            timeStr: String,
            format: String? = DEF_FORMAT
        ): IntArray? {
            return if (TextUtils.isEmpty(timeStr)) null else millis2Array(
                string2Millis(timeStr)
            )
        }

        /**
         * 将时间转换天数、小时数、分钟数、秒数
         *
         * @param date 时间
         * @return int[0]: 天数 <br></br> int[1]: 小时数 <br></br> int[2]: 分钟数 <br></br> int[3]: 秒数
         */
        fun millis2Array(date: Date?): IntArray? {
            return if (date == null) null else millis2Array(date.time)
        }

        /**
         * 将时间转换天数、小时数、分钟数、秒数
         *
         * @param millis 毫秒数
         * @return int[0]: 天数 <br></br> int[1]: 小时数 <br></br> int[2]: 分钟数 <br></br> int[3]: 秒数
         */
      private  fun millis2Array(millis: Long): IntArray {
            val secondDiff = millis / 1000
            val days = (secondDiff / (60 * 60 * 24)).toInt()
            val hours = ((secondDiff - days * (60 * 60 * 24)) / (60 * 60)).toInt()
            val minutes =
                ((secondDiff - days * (60 * 60 * 24) - hours * (60 * 60)) / 60).toInt()
            val seconds =
                (secondDiff - days * (60 * 60 * 24) - hours * (60 * 60) - minutes * 60).toInt()
            return intArrayOf(days, hours, minutes, seconds)
        }
        /**
         * 计算时间差
         *
         * @param startTime 起始时间
         * @param endTime   结束时间
         * @param format    时间格式
         * @return 毫秒级时间差
         */
        /**
         * 计算时间差
         *
         * @param startTime 起始时间
         * @param endTime   结束时间
         * @return 毫秒级时间差
         */
        @JvmOverloads
        fun caculateTimeDiff(
            startTime: Any,
            endTime: Any,
            format: String? = DEF_FORMAT
        ): Long {
            val milliStart: Long = when (startTime) {
                is String -> {
                    string2Millis(startTime, format)
                }
                is Long -> {
                    startTime
                }
                is Date -> {
                    startTime.time
                }
                else -> {
                    MyLog.e(
                        TAG,
                        "Error startTime in the caculateTimeDiff () method ========> startTime: $startTime"
                    )
                    throw UnsupportedOperationException("startTime foramt error")
                }
            }
            val milliEnd: Long = when (endTime) {
                is String -> {
                    string2Millis(endTime, format)
                }
                is Long -> {
                    endTime
                }
                is Date -> {
                    endTime.time
                }
                else -> {
                    MyLog.e(
                        TAG,
                        "Error endTime in the caculateTimeDiff () method ========> endTime: $endTime"
                    )
                    throw UnsupportedOperationException("endTime foramt error")
                }
            }
            return milliEnd - milliStart
        }

        /**
         * 计算时间差
         *
         * @param startTime 开始时间
         * @param endTime   结束时间
         * @return int[0]: 天数 <br></br> int[1]: 小时数 <br></br> int[2]: 分钟数 <br></br> int[3]: 秒数
         */
      private  fun caculateTimeDiffArray(startTime: Any, endTime: Any): IntArray {
            return caculateTimeDiffArray(startTime, endTime)
        }

        /**
         * 计算时间差
         *
         * @param startTime 开始时间
         * @param endTime   结束时间
         * @param format    时间格式
         * @return int[0]: 天数 <br></br> int[1]: 小时数 <br></br> int[2]: 分钟数 <br></br> int[3]: 秒数
         */
        fun caculateTimeDiffArray(
            startTime: Any,
            endTime: Any,
            format: String?
        ): IntArray {
            return millis2Array(
                caculateTimeDiff(
                    startTime,
                    endTime,
                    format
                )
            )
        }

        /**
         * 比较两个时间的大小
         *
         * @param t1 时间 1
         * @param t2 时间 2
         * @return `true`: t1 >= t2<br></br>`false`: t1 < t2
         */
        fun judgeTime(t1: Any, t2: Any): Boolean {
            return caculateTimeDiff(t2, t1) >= 0
        }
    }

    init {
        throw UnsupportedOperationException("cannot be instantiated")
    }
}