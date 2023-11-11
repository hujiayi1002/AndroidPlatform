package com.ocse.baseandroid.impl

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.ocse.baseandroid.R
import com.ocse.baseandroid.base.BaseApplication
import com.ocse.baseandroid.utils.MyLog
import com.ocse.baseandroid.utils.ObtainApplication
import com.ocse.baseandroid.utils.ToastUtil
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator

class AppManagerImpl(mContext: Context) : DefaultRefreshHeaderCreator {
    private val mContext: Context = mContext
    private val instance by lazy { this }
    private var count: Int = 0
    override fun createRefreshHeader(context: Context, layout: RefreshLayout): RefreshHeader {
        layout.setEnableHeaderTranslationContent(false)
            .setEnableOverScrollDrag(false)
        //全局设置主题颜色
        layout.setPrimaryColorsId(R.color.bgColor, android.R.color.black)
        val classicsFooter = ClassicsHeader(mContext)
        ClassicsFooter(mContext)
        return classicsFooter
    }

    var url = ""
    fun initBaseUrl(baseUrl: String): AppManagerImpl {
        if ("/" == baseUrl.substring(baseUrl.length - 1)) {
            url = baseUrl
        } else {
            throw Exception("必须以/结尾")
        }
        return instance
    }

    fun getBaseUrl(): String {
        return url
    }

    fun setLogEnable(enable: Boolean): AppManagerImpl {
        MyLog.setLoggerEnable(enable)
        return instance
    }

    /**
     * 是否弹窗展示app在后台
     */

    fun setBackgroundToast(showToast: Boolean): AppManagerImpl {
        ObtainApplication.app?.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                BaseApplication.activities.add(activity)
            }

            override fun onActivityStarted(activity: Activity) {
                count++
            }

            override fun onActivityResumed(activity: Activity) {}

            override fun onActivityPaused(activity: Activity) {}

            override fun onActivityStopped(activity: Activity) {
                count--
                if (count <= 0) {
                    MyLog.e("onActivityCreated: $count")
                    if (showToast) {
                        ToastUtil.show("当前APP已经不在前台，请谨慎操作")
                    }
                }
            }

            override fun onActivityDestroyed(activity: Activity) {
                BaseApplication.activities.remove(activity)
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }
        })
        return instance
    }
}