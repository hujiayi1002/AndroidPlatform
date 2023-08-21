package com.ocse.baseandroid.utils

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
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
     * 获取网络活动信息 判断网络是否连接
     *
     * 需添加权限 `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>`
     *
     * @return NetworkInfo
     */
    fun getActiveNetworkInfo(content: Context?): Boolean {
        return if (content == null) {
            false
        } else try {
            val conn = content.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                if (conn.activeNetwork == null) {
                    return false
                }
                val networkCapabilities =
                    conn.getNetworkCapabilities(conn.activeNetwork) ?: return false
                return (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_SUPL)
                        || networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                        || networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_MMS)
                        || networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_FOTA))
            }
            conn.activeNetworkInfo != null && conn.isActiveNetworkMetered
        } catch (e: Exception) {
            false
        }
    }

    /**
     * 判断网络是否连接
     *
     * 需添加权限 `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>`
     *
     * @return `true`: 是<br></br>`false`: 否
     */

    fun getUrl(url: String): String {
        return url
    }
}