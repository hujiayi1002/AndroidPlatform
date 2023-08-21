package com.ocse.baseandroid.base

import android.app.Activity
import android.app.Application
import io.reactivex.plugins.RxJavaPlugins
import java.util.*
import kotlin.system.exitProcess


open class BaseApplication : Application() {

    companion object {
        val activities = Stack<Activity>()
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { this }
    }

    private var count = 0

    override fun onCreate() {
        super.onCreate()
        RxJavaPlugins.setErrorHandler {
            //这里处理所有的Rxjava异常
            it.printStackTrace()
        }

    }




    /**
     * 退出应用
     */
    open fun exitApp() {
        // 方式1：android.os.Process.killProcess（）
        android.os.Process.killProcess(android.os.Process.myPid())
        // 方式2：System.exit()
        // System.exit() = Java中结束进程的方法：关闭当前JVM虚拟机
        exitProcess(0)
    }

}