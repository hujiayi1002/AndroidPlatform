package com.ocse.baseandroid.utils

import android.widget.Toast

class ToastUtil {
    companion object {
        fun show(message: String?) {
            if (!isShowToast()) {
                Toast.makeText(ObtainApplication.app, message, Toast.LENGTH_SHORT)
                    .show()
            }
        }

        /**
         * Prevent continuous click, jump two pages
         */
        private var lastToastTime: Long = 0
        private const val TIME: Long = 1500

        private fun isShowToast(): Boolean {
            val time = System.currentTimeMillis()
            if (time - lastToastTime < TIME) {
                return true
            }
            lastToastTime = time
            return false
        }
    }
}