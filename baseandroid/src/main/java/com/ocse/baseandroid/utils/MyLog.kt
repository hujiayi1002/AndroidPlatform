package com.ocse.baseandroid.utils

import android.util.Log


class MyLog {
    constructor()
    constructor(msg: String?) {
            Log.e(TAG, "$msg")
    }

    companion object {
        var TAG = "TAG"
        private var enable = false

        fun setTag(tag: String) {
            TAG = tag
        }

        fun setLoggerEnable(isEnable: Boolean) {
            enable = isEnable
        }

        fun e(msg: String?) {
            if (enable) {
                Log.e(TAG, "$msg")
            }
        }

        fun e(tag: String, msg: String?) {
            if (enable) {
                Log.e(tag, "$msg")
            }
        }

        fun d(msg: String?) {
            if (enable && !msg.isNullOrEmpty()) {
                Log.d(TAG, "" + msg)
            }

        }

        fun i(msg: String?) {
            if (enable &&!msg.isNullOrEmpty()) {
                Log.i(TAG, "" + msg)
            }

        }

    }
//    }
}