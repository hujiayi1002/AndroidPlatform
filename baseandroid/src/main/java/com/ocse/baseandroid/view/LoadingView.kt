package com.ocse.baseandroid.view

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import com.ocse.baseandroid.R

class LoadingView private constructor(context: Context) : Dialog(context, R.style.dialog) {

    class Builder(context: Context) {
        private lateinit var loadAnimation: Animation
        private val mLayout: View
        private var mMessage: TextView
        private var image: ImageView
        private var canceledOnTouchOutside = false
        private val mDialog: LoadingView = LoadingView(context)

        /**
         * 设置 Message
         */
        fun setMessage(message: String): Builder {
            mMessage.visibility = View.VISIBLE
            mMessage.text = message
            return this
        }

        fun setCanceledOnTouchOutside(cancel: Boolean): Builder {
            canceledOnTouchOutside = cancel //用户不可以点击外部来关闭 Dialog
            return this
        }

        fun create(): LoadingView {
            mDialog.setContentView(mLayout)
            mDialog.window?.setBackgroundDrawable(ColorDrawable(0))
            //mDialog.window?.setDimAmount(0f)
            mDialog.setCancelable(true) //用户可以点击后退键关闭 Dialog
            mDialog.setCanceledOnTouchOutside(canceledOnTouchOutside) //用户不可以点击外部来关闭 Dialog
            startAnimation(mDialog.context)
            mDialog.setOnDismissListener {
                if (::loadAnimation.isInitialized) loadAnimation.reset()
            }
            return mDialog
        }


        init {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            //加载布局文件
            mLayout = inflater.inflate(R.layout.loading_view, null, false)
            //添加布局文件到 Dialog
            mDialog.addContentView(
                mLayout,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )
            mMessage = mLayout.findViewById(R.id.tvLoading)
            image = mLayout.findViewById(R.id.image)
        }

        private fun startAnimation(context: Context) {
            loadAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate)
            val lin = LinearInterpolator()
            loadAnimation.interpolator = lin
            val image = mLayout.findViewById<ImageView>(R.id.image)
            image.startAnimation(loadAnimation)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

    }
}