package com.ocse.baseandroid.base

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.gyf.immersionbar.ImmersionBar
import com.ocse.baseandroid.R
import com.ocse.baseandroid.impl.saveAs
import com.ocse.baseandroid.utils.ActivityStackUtils
import com.ocse.baseandroid.utils.ToastUtil
import com.ocse.baseandroid.view.LoadingView
import com.ocse.baseandroid.view.TitleBarView
import java.lang.reflect.ParameterizedType
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
        transparentStatusBar(true)
        ActivityStackUtils.addActivity(this)
        loadingViewView = LoadingView.Builder(this).create()
        initContent()
        initView()
        initData()
    }

    abstract fun initView()
    abstract fun initData()
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
        if (!loadingViewView.isShowing) {
            loadingViewView.show()
        }
    }

    open fun loadingDismiss() {
        loadingViewView.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityStackUtils.removeActivity(this)
    }
}