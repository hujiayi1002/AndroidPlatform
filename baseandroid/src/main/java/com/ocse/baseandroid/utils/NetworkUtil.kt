package com.ocse.baseandroid.utils

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.provider.Settings

/**
 * Function: 网络相关工具类
 * Desc:
 */
object NetworkUtil {
    /**
     * 打开网络设置界面
     */
    fun openWirelessSettings(context: Context?) {
        context?.startActivity(
            Intent(Settings.ACTION_WIRELESS_SETTINGS).setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
            )
        )
    }

    /**
     * 获取活动网络信息
     *
     * 需添加权限 `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>`
     *
     * @return NetworkInfo
     */
    private fun getActiveNetworkInfo(content: Context?): NetworkInfo? {
        return if (content == null) {
            null
        } else (content.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
    }

    /**
     * 判断网络是否连接
     *
     * 需添加权限 `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>`
     *
     * @return `true`: 是<br></br>`false`: 否
     */
    fun isConnected(content: Context?): Boolean {
        val info = getActiveNetworkInfo(content)
        return info != null && info.isConnected
    }

    fun getUrl(url: String): String {
        return url
    }
}