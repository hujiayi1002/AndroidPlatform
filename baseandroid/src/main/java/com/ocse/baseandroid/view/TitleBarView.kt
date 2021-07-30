package com.ocse.baseandroid.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.gyf.immersionbar.ImmersionBar
import com.ocse.baseandroid.R
import com.ocse.baseandroid.databinding.LayoutToolbarBinding
import org.jetbrains.annotations.NotNull

class TitleBarView : RelativeLayout {
    private lateinit var ivBack: ImageView
    private lateinit var imgRight: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var tvRight: TextView
    private lateinit var relBack: RelativeLayout
    private lateinit var toolbar: Toolbar

    constructor(context: Context, attributes: AttributeSet) : super(context, attributes) {
        initView(context, attributes)
    }

    constructor(context: Context, attributes: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributes,
        defStyleAttr
    ) {
        initView(context, attributes)
    }

    constructor(
        context: Context,
        attributes: AttributeSet,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attributes, defStyleAttr, defStyleRes) {
        initView(context, attributes)
    }

    @SuppressLint("CustomViewStyleable")
    private fun initView(context: Context, attributes: AttributeSet) {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_toolbar, this, true);
        val viewBinding = LayoutToolbarBinding.bind(view)
        val typedArray = context.obtainStyledAttributes(attributes, R.styleable.TitleBarView)
        ivBack = viewBinding.ivBack
        ivBack.visibility = VISIBLE
        imgRight = viewBinding.imgRight
        tvTitle = viewBinding.tvTitle
        tvRight = viewBinding.tvRight
        relBack = viewBinding.relBack
        toolbar = viewBinding.toolbar

        //getDimensionPixelSize
        //setImageResource

        tvTitle.text = typedArray.getString(R.styleable.TitleBarView_titleText)
        tvRight.text = typedArray.getString(R.styleable.TitleBarView_rightText)
        toolbar.elevation = 3f
        tvTitle.setTextColor(typedArray.getColor(R.styleable.TitleBarView_textColor, Color.BLACK))
        tvRight.setTextColor(
            typedArray.getColor(
                R.styleable.TitleBarView_rightTextColor,
                Color.BLACK
            )
        )

//        ivBack.setImageResource(typedArray.getInt(R.styleable.TitleBarView_icon_back,
//            R.drawable.ic_baseline_arrow_back_24))
        ivBack.setImageDrawable(typedArray.getDrawable(R.styleable.TitleBarView_icon_back))
        imgRight.setImageDrawable(typedArray.getDrawable(R.styleable.TitleBarView_rightImg))
        toolbar.background = typedArray.getDrawable(R.styleable.TitleBarView_backgroundColor)

        typedArray.recycle()
    }

    fun setOnLeftClick(onClickListener: OnClickListener): TitleBarView {
        relBack.setOnClickListener(onClickListener)
        return this
    }

    fun setSetLeftImg(@NotNull resId: Int): TitleBarView {
        ivBack.setImageResource(resId)
        return this
    }

    fun setTitle(@NotNull title: String): TitleBarView {
        tvTitle.text = title
        return this
    }

    fun setRightText(@NotNull rightText: String): TitleBarView {
        tvRight.visibility = View.VISIBLE
        tvRight.text = rightText
        return this
    }

    fun setTitleColor(@NotNull colorId: Int): TitleBarView {
        tvTitle.setTextColor(colorId)
        return this
    }

    fun setRightTextColor(@NotNull colorId: Int): TitleBarView {
        tvRight.setTextColor(colorId)
        return this
    }

    fun setTransparentStatus(activity: Activity): TitleBarView {
        setTransparentStatus(activity, false)
        return this
    }

    fun setTransparentStatus(activity: Activity, darkFontColor: Boolean): TitleBarView {
        ImmersionBar.with(activity).transparentStatusBar().statusBarDarkFont(darkFontColor).init()
        return this
    }


}