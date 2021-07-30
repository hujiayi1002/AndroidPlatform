package com.ocse.baseandroid.utils

import android.util.Log
import com.ocse.baseandroid.BuildConfig


class Logger {
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
                Log.e(TAG, "_________________________________________________________________")
                Log.e(TAG, "$msg")
            }
        }

        fun e(tag: String, msg: String?) {
            if (enable) {
                Log.e(TAG, "_________________________________________________________________")
                Log.e(tag, "$msg")
            }
        }

        fun d(msg: String?) {
            if (BuildConfig.DEBUG && !msg.isNullOrEmpty()) {
                Log.d(TAG, "_________________________________________________________________")
                Log.d(TAG, "" + msg)
            }

        }

        fun i(msg: String?) {
            if (BuildConfig.DEBUG && !msg.isNullOrEmpty()) {
                Log.i(TAG, "_________________________________________________________________")
                Log.i(TAG, "" + msg)
            }

        }

    }
//    }
}