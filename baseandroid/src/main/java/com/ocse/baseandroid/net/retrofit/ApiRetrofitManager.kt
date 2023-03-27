package com.ocse.baseandroid.net.retrofit

import com.ocse.baseandroid.utils.ToastUtil.Companion.show

/**
 * @author hujiayi
 */
class ApiRetrofitManager {
    companion object {
        var url = ""
        fun init(baseUrl: String) {
            try {
                if ("/" == baseUrl.substring(baseUrl.length - 1)) {
                    url = baseUrl
                } else {
                    show("必须以/结尾")
                }
            } catch (e: Exception) {
                show(e.message)
            }
        }
        fun getBaseUrl(): String {
            return url
        }
    }


}