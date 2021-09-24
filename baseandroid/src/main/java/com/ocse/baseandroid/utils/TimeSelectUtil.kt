package com.ocse.baseandroid.utils

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.TextView
import java.util.*

open class TimeSelectUtil {
    companion object {

        fun getDateTimeSeconds(mContext: Context, tv: TextView) {
            tv.setOnClickListener {
                val c = Calendar.getInstance()
                DatePickerDialog(
                    mContext,
                    DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                        val monthString = if ((month + 1) < 10) {
                            "0${month + 1}"
                        } else {
                            (month + 1).toString()
                        }
                        val day = if (dayOfMonth < 10) {
                            "0${dayOfMonth}"
                        } else {
                            dayOfMonth.toString()
                        }
                        TimePickerDialog(
                            mContext, AlertDialog.THEME_HOLO_LIGHT,
                            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                                val hour = if (hourOfDay < 10) {
                                    "0${hourOfDay}"
                                } else {
                                    hourOfDay.toString()
                                }
                                val min = if (minute < 10) {
                                    "0${minute}"
                                } else {
                                    minute.toString()
                                }
                                tv.text = "${year}-${monthString}-${day} ${hour}:${min}"
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
        }

        fun initHourMin(mContext: Context, tv: TextView) {
            tv.setOnClickListener {
                val c = Calendar.getInstance()
                TimePickerDialog(
                    mContext, AlertDialog.THEME_HOLO_LIGHT,
                    TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                        val hour = if (hourOfDay < 10) {
                            "0${hourOfDay}"
                        } else {
                            hourOfDay.toString()
                        }
                        val min = if (minute < 10) {
                            "0${minute}"
                        } else {
                            minute.toString()
                        }
                        tv.text = "${hour}:${min}"
                    },
                    c.get(Calendar.HOUR_OF_DAY),
                    c.get(Calendar.MINUTE),
                    true
                ).show()
            }

        }

        fun initDateYearMonthDay(mContext: Context, tv: TextView) {
            tv.setOnClickListener {
                val c = Calendar.getInstance()
                DatePickerDialog(
                    mContext,
                    DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                        val monthString = if ((month + 1) < 10) {
                            "0${month + 1}"
                        } else {
                            (month + 1).toString()
                        }
                        val day = if (dayOfMonth < 10) {
                            "0${dayOfMonth}"
                        } else {
                            dayOfMonth.toString()
                        }
                        tv.text = "${year}-${monthString}-${day}"
                    },
                    c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),
                    c.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }

        fun initDateYearMonthDay(mContext: Context, tv: TextView, callBack: TimeCallBack) {
            tv.setOnClickListener {
                val c = Calendar.getInstance()
                DatePickerDialog(
                    mContext,
                    DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                        val monthString = if ((month + 1) < 10) {
                            "0${month + 1}"
                        } else {
                            (month + 1).toString()
                        }
                        val day = if (dayOfMonth < 10) {
                            "0${dayOfMonth}"
                        } else {
                            dayOfMonth.toString()
                        }
                        tv.text = "${year}-${monthString}-${day}"
                        callBack.onDateSelected("${year}-${monthString}-${day}")
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
    }
}