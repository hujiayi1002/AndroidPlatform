package com.ocse.baseandroid.utils

import com.ocse.baseandroid.utils.SharePreferenceUtil


/**
 * @author hujiayi
 */
object AddTokenUtils {
    @JvmStatic
    fun addToken(url: String): String {
        var addToken = "?"
        if (url.contains("?")) {
            addToken = "&"
        }
        addToken += "token=" + SharePreferenceUtil.getString("token")
        return url + addToken
    }

}