package com.ocse.androidbaselib

import com.ocse.baseandroid.base.BaseApplication
import com.ocse.baseandroid.impl.AppManagerImpl
import com.ocse.baseandroid.net.retrofit.ApiRetrofitManager
import com.ocse.baseandroid.utils.MyLog

class App : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        ApiRetrofitManager.init("http://10.81.108.88:8090/")
        val appManagerImpl = AppManagerImpl(this)
        appManagerImpl.setBackgroundToast(true).initBaseUrl("http://10.81.108.88:8090/")
        MyLog.setLoggerEnable(true)
    }
}