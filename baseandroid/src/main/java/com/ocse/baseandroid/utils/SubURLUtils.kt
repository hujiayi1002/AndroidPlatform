package com.ocse.baseandroid.utils

class SubURLUtils {
    companion object{
    fun subUrl(url: String, name: String): String? {
        var str: String? = null
        val param = HashMap<String, String>()
        if (url.indexOf("?") > 0) {
            val urlParameterName = url.split("?")[1]
            var strs = urlParameterName.split("&")
            for (i in strs.indices) {
                param[strs[i].split("=")[0]] = strs[i].split("=")[1];//前者为参数名称，后为参数值
            }
            if (!param[name].isNullOrEmpty()) {
                str = param[name]
            }
        }
        return str
    }
    }
}