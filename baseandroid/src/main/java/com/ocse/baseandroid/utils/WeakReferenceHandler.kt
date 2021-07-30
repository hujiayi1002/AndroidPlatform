package com.ocse.baseandroid.utils

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.os.Message
import java.lang.ref.WeakReference


 open class WeakReferenceHandler(activity: Activity) :
     Handler(Looper.getMainLooper()) {
     private var mActivity: WeakReference<Activity> = WeakReference(activity)
     override fun handleMessage(msg: Message) {
         super.handleMessage(msg)
         mActivity.get() ?: return
     }
 }