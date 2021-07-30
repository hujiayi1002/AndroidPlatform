package com.ocse.baseandroid.utils

import java.security.MessageDigest

object MD5Util {
    //加密算法
    fun encode(str: String?): String? {
        val md5 = "MD5"
        charArrayOf(
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
        )
        return if (str == null) {
            null
        } else try {
            val messageDigest =
                MessageDigest.getInstance(md5)
            messageDigest.update(str.toByteArray(charset("utf-8")))
            messageDigest.digest().toString()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
    //    private static String getFormattedText(byte[] bytes) {
    //        int len = bytes.length;
    //        StringBuilder buf = new StringBuilder(len * 2);
    //        char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
    //                '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    //        // 把密文转换成十六进制的字符串形式
    //        for (int j = 0; j < len; j++) {
    //            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
    //            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
    //        }
    //        return buf.toString();
    //    }
}