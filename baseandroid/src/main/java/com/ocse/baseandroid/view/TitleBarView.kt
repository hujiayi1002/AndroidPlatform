package com.ocse.baseandroid.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.ScreenUtils
import com.gyf.immersionbar.ImmersionBar
import com.ocse.baseandroid.R
import com.ocse.baseandroid.databinding.LayoutToolbarBinding
import com.ocse.baseandroid.utils.DensityUtil
import org.jetbrains.annotations.NotNull

class TitleBarView : RelativeLayout {
    private lateinit var ivBack: ImageView
    private lateinit var imgRight: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var tvRight: TextView
    private lateinit var relBack: RelativeLayout
    private lateinit var toolbar: Toolbar

    private var mContext: Context

    constructor(context: Context, attributes: AttributeSet) : super(context, attributes) {
        this.mContext = context
        initAttributes(context, attributes)
    }

    constructor(context: Context, attributes: AttributeSet, defStyleAttr: Int) : super(
        context, attributes, defStyleAttr
    ) {
        this.mContext = context
        initAttributes(context, attributes)
    }

    constructor(
        context: Context,
        attributes: AttributeSet,
        defStyleAttr: Int,
        defStyleRes: Int,
    ) : super(context, attributes, defStyleAttr, defStyleRes) {
        this.mContext = context
        initAttributes(context, attributes)
        initView(context)
    }

    @SuppressLint("CustomViewStyleable")
    private fun initAttributes(context: Context, attributes: AttributeSet) {
        //val view = LayoutInflater.from(context).inflate(R.layout.layout_toolbar, this, true);
        //val viewBinding = LayoutToolbarBinding.bind(view)
        val typedArray = context.obtainStyledAttributes(attributes, R.styleable.TitleBarView)
        val heightParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
        val widthParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        //ivBack = viewBinding.ivBack
        //ivBack.visibility = VISIBLE
        //imgRight = viewBinding.imgRight
        //tvTitle = viewBinding.tvTitle
        //tvRight = viewBinding.tvRight
        //relBack = viewBinding.relBack
        //toolbar = viewBinding.toolbar

        //getDimensionPixelSize
        //setImageResource
        val title = typedArray.getString(R.styleable.TitleBarView_titleText)
        //val mDividerHeight = typedArray.getDimensionPixelSize(R.styleable.titleViewHeight,DensityUtil.dp2px(68f))

        tvTitle.text = if (title.isNullOrEmpty()) getAppName() else title
        tvRight.text = typedArray.getString(R.styleable.TitleBarView_rightText)
        toolbar.elevation = 3f
        tvTitle.setTextColor(typedArray.getColor(R.styleable.TitleBarView_textColor, Color.BLACK))
        tvRight.setTextColor(
            typedArray.getColor(
                R.styleable.TitleBarView_rightTextColor, Color.BLACK
            )
        )
        ivBack.setImageDrawable(typedArray.getDrawable(R.styleable.TitleBarView_icon_back))
        imgRight.setImageDrawable(typedArray.getDrawable(R.styleable.TitleBarView_rightImg))
        toolbar.background = typedArray.getDrawable(R.styleable.TitleBarView_backgroundColor)
        typedArray.recycle()
    }
 fun   initView( context:Context){}
    fun   setViewAttributes( context:Context){}
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
        } catch (e: PackageManager.NameNotFoundException) {
        }
        return null
    }

    /**
     * 设置返回按钮隐藏
     */
    fun setBackGone(): TitleBarView {
        relBack.visibility = View.INVISIBLE
        return this
    }

    /**
     *   设置背景色
     */
    fun setBackground(@NotNull resId: Int): TitleBarView {
        toolbar.setBackgroundColor(ContextCompat.getColor(mContext, resId))
        return this
    }

    /**
     *  左边返回按钮点击事件
     */
    fun setOnLeftClick(onClickListener: OnClickListener): TitleBarView {
        relBack.setOnClickListener(onClickListener)
        return this
    }

    /**
     *  设置返回按钮
     */
    fun setBackImg(@NotNull resId: Int): TitleBarView {
        ivBack.setImageResource(resId)
        return this
    }

    /**
     * 设置标题
     */
    fun setTitle(title: String?): TitleBarView {
        tvTitle.text = title
        return this
    }

    /**
     * 设置右边文字
     */
    fun setRightText(@NotNull rightText: String): TitleBarView {
        tvRight.visibility = View.VISIBLE
        tvRight.text = rightText
        return this
    }

    /**
     * 设置字体颜色
     */
    fun setTitleColor(@NotNull colorId: Int): TitleBarView {
        tvTitle.setTextColor(colorId)
        return this
    }

    /**
     *   设置右边文字颜色
     */
    fun setRightTextColor(@NotNull colorId: Int): TitleBarView {
        tvRight.setTextColor(colorId)
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