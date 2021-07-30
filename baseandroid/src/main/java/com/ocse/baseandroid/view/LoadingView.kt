package com.ocse.baseandroid.view

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ocse.baseandroid.R

class LoadingView private constructor(context: Context) : Dialog(context, R.style.dialog) {
    class Builder(context: Context) {
        private val mLayout: View
        private var mMessage: TextView
        private val mDialog: LoadingView = LoadingView(context)

        /**
         * 设置 Message
         */
        fun setMessage(message: String): Builder {
            mMessage.visibility = View.VISIBLE
            mMessage.text = message
            return this
        }

        fun create(): LoadingView {
            mDialog.setContentView(mLayout)
            mDialog.setCancelable(true) //用户可以点击后退键关闭 Dialog
            mDialog.setCanceledOnTouchOutside(false) //用户不可以点击外部来关闭 Dialog
            return mDialog
        }


        init {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            //加载布局文件
            mLayout = inflater.inflate(R.layout.loading_view, null, false)
            //添加布局文件到 Dialog
            mDialog.addContentView(mLayout,
                ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT))
            mMessage = mLayout.findViewById(R.id.tvLoading)
//            val a =
//                AnimationUtils.loadAnimation(context, R.anim.rotate)
//            val lin = LinearInterpolator()
//            a.interpolator = lin
//            mLayout.image.startAnimation(a)
        }
    }
}