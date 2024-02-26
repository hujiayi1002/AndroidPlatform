package com.ocse.androidbaselib

import com.ocse.baseandroid.base.BaseApplication
import com.ocse.baseandroid.impl.AppManagerImpl

class App : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        AppManagerImpl
            .setBackgroundToast(false)
            .initBaseUrl("http://36.112.48.104:10010/aqjdjchnytjjg/")
//            .initBaseUrl("http://10.81.108.88:8090")
            .setLogEnable(true)
    }
}