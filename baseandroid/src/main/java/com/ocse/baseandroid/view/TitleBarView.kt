package com.ocse.baseandroid.view

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.gyf.immersionbar.ImmersionBar
import com.ocse.baseandroid.R

class TitleBarView : RelativeLayout {
    private var mContext: Context
    lateinit var toolbar: Toolbar
    lateinit var relBack: RelativeLayout

    lateinit var tvTitle: TextView
    lateinit var tvRight: TextView
    lateinit var imgRight: ImageView
    lateinit var ivBack: ImageView

    constructor(context: Context, attributes: AttributeSet) : super(context, attributes) {
        this.mContext = context
        initViews()
    }

    constructor(context: Context, attributes: AttributeSet, defStyleAttr: Int) : super(
        context, attributes, defStyleAttr
    ) {
        this.mContext = context
        initViews()
    }

    private fun initViews() {
        LayoutInflater.from(context).inflate(R.layout.layout_toolbar, this, true)
        toolbar = findViewById(R.id.toolbar)
        relBack = findViewById(R.id.relBack)
        tvTitle = findViewById(R.id.tvTitle)
        tvRight = findViewById(R.id.tvRight)
        imgRight = findViewById(R.id.imgRight)
        ivBack = findViewById(R.id.ivBack)
    }

    /**
     * appname
     */
    private fun getAppName(): CharSequence? {
        try {
            val packageManager = mContext.packageManager
            val packageInfo = packageManager.getPackageInfo(
                mContext.packageName, 0
            )
            val labelRes = packageInfo.applicationInfo.labelRes
            return mContext.resources.getText(labelRes)
        } catch (_: PackageManager.NameNotFoundException) {
        }
        return null
    }

    /**
     * 设置返回按钮隐藏
     */
    fun setLeftArrowGone(): TitleBarView {
        relBack.visibility = View.INVISIBLE
        return this
    }

    fun setLeftArrowResource(res: Int): TitleBarView {
        ivBack.visibility = View.VISIBLE
        ivBack.setImageResource(res)
        return this
    }

    fun setRightImgResource(res: Int): TitleBarView {
        imgRight.visibility = View.VISIBLE
        imgRight.setImageResource(res)
        return this
    }

    fun setRightText(rightText: String): TitleBarView {
        tvRight.visibility = View.VISIBLE
        tvRight.text = rightText
        return this
    }

    fun setRightTextColor(res: Int): TitleBarView {
        tvRight.setTextColor(ContextCompat.getColor(mContext, res))
        return this
    }

    fun setTitleBarColor(res: Int): TitleBarView {
        toolbar.setBackgroundColor(ContextCompat.getColor(mContext, res))
        return this
    }

    fun setTitleTextColor(res: Int): TitleBarView {
        tvTitle.setTextColor(ContextCompat.getColor(mContext, res))
        return this
    }

    fun setStatusColor(activity: Activity, res: Int, darkFontColor: Boolean): TitleBarView {
        ImmersionBar.with(activity).fitsSystemWindows(true).statusBarColor(res)
            .statusBarDarkFont(darkFontColor).init()
        return this
    }

    /**
     * 设置标题
     */
    fun setTitle(title: String?): TitleBarView {
        if (title.isNullOrEmpty()) {
            tvTitle.text = getAppName()
        } else {
            tvTitle.text = title
        }
        return this
    }

    /**
     * 状态栏透明颜色
     */
    fun setTransparentStatus(activity: Activity): TitleBarView {
        setTransparentStatus(activity, false)
        return this
    }

    fun setTransparentStatus(activity: Activity, darkFontColor: Boolean): TitleBarView {
        ImmersionBar.with(activity).transparentStatusBar().statusBarDarkFont(darkFontColor).init()
        return this
    }
}