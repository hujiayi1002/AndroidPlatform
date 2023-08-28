package com.ocse.baseandroid.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ImmersionBar
import com.ocse.baseandroid.utils.ActivityStackUtils
import com.ocse.baseandroid.utils.ToastUtil
import com.ocse.baseandroid.view.LoadingView
import kotlin.system.exitProcess


abstract class RootActivity : AppCompatActivity {
    private lateinit var loadingViewView: LoadingView
    private var hash: Int = 0
    private var lastClickTime: Long = 0
    private var spaceTime: Long = 2000
    private val mContext by lazy { this@RootActivity }
    private var exitTime = 0L


    constructor() : super()
    constructor(getLayoutId: Int) : super(getLayoutId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImmersionBar.with(this).transparentBar().statusBarDarkFont(true).init()
        ActivityStackUtils.addActivity(this)
        loadingViewView = LoadingView.Builder(this).create()
        initContent()
    }

    infix fun start(cla: Class<*>) {
        startActivity(Intent(mContext, cla))
    }

    infix fun start(intent: Intent) {
        startActivity(intent)
    }

    abstract fun initContent()
    infix fun View.singleClick(clickAction: () -> Unit) {
        this.setOnClickListener {
            if (this.hashCode() != hash) {
                hash = this.hashCode()
                lastClickTime = System.currentTimeMillis()
                clickAction()
            } else {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastClickTime > spaceTime) {
                    lastClickTime = System.currentTimeMillis()
                    clickAction()
                }

            }
        }
    }


    /**
     * 双击退出
     */
    open fun onDoubleBackPressed() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            ToastUtil.show("再按一次退出程序")
            exitTime = System.currentTimeMillis()
        } else {
            finish()
            android.os.Process.killProcess(android.os.Process.myPid())
            exitProcess(0)
        }
    }


    open fun setBarColor(color: Int, isDarkFont: Boolean) {
        ImmersionBar.with(this).statusBarColor(color).statusBarDarkFont(isDarkFont).init()

    }

    open fun transparentStatusBar(isDarkFont: Boolean) {
        ImmersionBar.with(this).transparentStatusBar().statusBarDarkFont(isDarkFont).init()
    }


    open fun loadingShow() {
        if (!loadingViewView.isShowing)
            loadingViewView.show()
    }

    open fun loadingDismiss() {
        loadingViewView.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityStackUtils.removeActivity(this)
    }
}