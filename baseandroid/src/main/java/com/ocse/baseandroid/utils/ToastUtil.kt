package com.ocse.baseandroid.utils

import android.view.Gravity
import android.widget.Toast

class ToastUtil {
    companion object {
        fun show(message: String?) {
            if (!isShowToast()) {
                Toast.makeText(ObtainApplication.app, message, Toast.LENGTH_SHORT)
                    .show()
            }
        }

        fun showLong(message: String?) {
            if (!isShowToast()) {
                Toast.makeText(ObtainApplication.app, message, Toast.LENGTH_LONG)
                    .show()
            }
        }

        /**
         * @param message String? 消息
         */
        fun showCenterGravity(message: String?) {
            if (!isShowToast()) {
                val toast = Toast.makeText(ObtainApplication.app, message, Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }
        }

        /**
         * @param message String? 消息
         * @param gravity Int 位置
         */
        fun showGravity(message: String?, gravity: Int) {
            if (!isShowToast()) {
                val toast = Toast.makeText(ObtainApplication.app, message, Toast.LENGTH_SHORT)
                toast.setGravity(gravity, 0, 0)
                toast.show()
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