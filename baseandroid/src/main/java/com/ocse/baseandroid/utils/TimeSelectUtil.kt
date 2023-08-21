package com.ocse.baseandroid.utils

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SetTextI18n", "SimpleDateFormat")
open class TimeSelectUtil {
    companion object {
        val c = Calendar.getInstance()
        fun getNow(tv: TextView) {
            tv.text = SimpleDateFormat("yyyy-MM:dd HH:mm:ss").format(System.currentTimeMillis())
        }

        fun getNow(): String {
            return SimpleDateFormat("yyyy-MM:dd HH:mm:ss").format(System.currentTimeMillis())
        }

        fun getDateTimeSeconds(tv: TextView) {
            tv.setOnClickListener {
                getDateTimeDialogSelect(tv.context, object : DateTimeDialogImpl {
                    override fun grtTime(time: String) {
                        tv.text = time
                    }

                })
            }
        }

        private fun getDateTimeDialogSelect(context: Context, impl: DateTimeDialogImpl) {
            DatePickerDialog(
                context, { _, year, month, dayOfMonth ->
                    TimePickerDialog(
                        context, { _, hourOfDay, minute ->
                            impl.grtTime(showText(year, month, dayOfMonth, hourOfDay, minute))
                        },
                        c.get(Calendar.HOUR_OF_DAY),
                        c.get(Calendar.MINUTE),
                        true
                    ).show()
                },
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        fun initHourMin(tv: TextView) {
            tv.setOnClickListener {
                val c = Calendar.getInstance()
                TimePickerDialog(
                    tv.context, AlertDialog.THEME_HOLO_LIGHT, { _, hourOfDay, minute ->
                        showText(tv, null, null, null, hourOfDay, minute)
                    },
                    c.get(Calendar.HOUR_OF_DAY),
                    c.get(Calendar.MINUTE),
                    true
                ).show()
            }

        }

        fun initYearMonthDay(tv: TextView) {
            tv.setOnClickListener {
                val c = Calendar.getInstance()
                DatePickerDialog(
                    tv.context,
                    { _, year, month, dayOfMonth ->
                        showText(tv, year, month, dayOfMonth, null, null)
                    },
                    c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),
                    c.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }

        fun initYearMonthDay(tv: TextView, callBack: TimeCallBack) {
            tv.setOnClickListener {
                DatePickerDialog(
                    tv.context, { _, year, month, dayOfMonth ->
                        showText(tv, year, month, dayOfMonth, null, null)
                        callBack.onDateSelected(tv.text.toString())
                    },
                    c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),
                    c.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }

        interface TimeCallBack {
            fun onDateSelected(date: String)
        }

        private fun showText(
            year: Int?,
            month: Int?,
            dayOfMonth: Int?,
            hourOfDay: Int?,
            minute: Int?,
        ): String {
            val timeStr = StringBuilder()
            year?.run { timeStr.append("$year") }
            month?.run {
                timeStr.append("-")
                if ((month + 1) < 10) {
                    timeStr.append("0")
                }
                timeStr.append("$month")
            }
            dayOfMonth?.run {
                timeStr.append("-")
                if (dayOfMonth < 10) {
                    timeStr.append("0")
                }
                timeStr.append("$dayOfMonth")
            }
            hourOfDay?.run {
                if (hourOfDay < 10) {
                    timeStr.append("0")
                }
                if (timeStr.isNotEmpty()) timeStr.append(" ")
                timeStr.append("$hourOfDay:")
            }
            minute?.run {
                if (minute < 10) {
                    timeStr.append("0")
                }
                timeStr.append("$minute:00")
            }

            return timeStr.toString()
        }

        private fun showText(
            tv: TextView,
            year: Int?,
            month: Int?,
            dayOfMonth: Int?,
            hourOfDay: Int?,
            minute: Int?,
        ) {
            tv.text = showText(year, month, dayOfMonth, hourOfDay, minute)
        }

    }

    interface DateTimeDialogImpl {
        fun grtTime(time: String)
    }

}