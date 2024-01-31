package com.ocse.baseandroid.utils

import android.util.Log

/**
 * 截取double中.00
 * @author 123
 *
 * @return
 * @date 2023/12/29 17:57
 */
class DoubleInterceptZeroUtil {
    companion object {
        /**
         *  截取double中.00
         * num 字符串型double
         * @return
         * @date 2023/12/29 17:58
         */
        fun doubleStrInterceptZero(num: String): String {
            var str =num
            try {
                val toDouble = num.toDouble()
                if (toDouble - toDouble.toInt() < 0.00001) {
                    str = toDouble.toString().split(".")[0]
                }
            } catch (e: Exception) {
                Log.e("DoubleInterceptZeroUtil", "doubleStrInterceptZero: ${e.message} ")
            }
            return str
        }
        /**
         *  截取double中.00
         * d double类型
         * @return
         * @date 2023/12/29 17:58
         */
        fun doubleInterceptZero(num: Double): String {
            var str = num.toString()
            try {
                if (num - num.toInt() < 0.00001) {
                    str = num.toString().split(".")[0]
                }
            } catch (e: Exception) {
                Log.e("DoubleInterceptZeroUtil", "doubleStrInterceptZero: ${e.message} ")
            }
            return str
        }
    }

}