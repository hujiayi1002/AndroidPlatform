package com.ocse.baseandroid.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * @author hujiayi
 */
object SharePreferenceUtil {
    fun saveString(key: String?, value: String?) {
        val sharedPreferences: SharedPreferences = ObtainApplication.app?.getSharedPreferences(
            ObtainApplication.app?.packageName,
            Context.MODE_PRIVATE
        )!!
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String?): String {
        val sharedPreferences: SharedPreferences = ObtainApplication.app?.getSharedPreferences(
            ObtainApplication.app?.packageName,
            Context.MODE_PRIVATE
        )!!
        return  if (sharedPreferences.getString(key, "").isNullOrEmpty()) "" else sharedPreferences.getString(key, "").toString()
    }
}