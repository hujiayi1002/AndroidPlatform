package com.ocse.baseandroid.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * @author hujiayi
 */
object SharePreferenceUtil {
    fun saveString(key: String?, value: String?) {
        val sharedPreferences: SharedPreferences = ObtainApplication.getApp()!!.getSharedPreferences(
            ObtainApplication.getApp()!!.packageName,
            Context.MODE_PRIVATE
        )!!
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.commit()
    }

    fun getString(key: String?): String? {
        val sharedPreferences: SharedPreferences = ObtainApplication.getApp()!!.getSharedPreferences(
            ObtainApplication.getApp()!!.packageName,
            Context.MODE_PRIVATE
        )!!
        return sharedPreferences.getString(key, "")
    }
}