package com.ocse.baseandroid.net.retrofit


/**
 * @author hujiayi
 */
class ApiRetrofitManager {
    companion object {
        var url = ""
        fun init(baseUrl: String) {
            if ("/" == baseUrl.substring(baseUrl.length - 1)) {
                url = baseUrl
            } else {
                throw java.lang.IllegalArgumentException("必须以/结尾")
            }
        }

        fun getBaseUrl(): String {
            return url
        }
    }


}