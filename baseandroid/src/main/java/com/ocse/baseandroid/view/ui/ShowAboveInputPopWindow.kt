package com.ocse.baseandroid.view.ui

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.PopupWindow
import com.ocse.baseandroid.utils.ObtainApplication

/**
 *  在输入法上面的弹窗
 */
class ShowAboveInputPopWindow : PopupWindow {
    /**
     *
     * @param context Context
     * @param layout Int 弹窗布局
     * @param root View activity跟布局
     * @constructor
     */
    constructor(context: Activity, layout: Int) : super(context) {
        val window = context.window
        window.decorView.viewTreeObserver.addOnGlobalLayoutListener {
            val popView = LayoutInflater.from(context).inflate(layout, null)
            setBackgroundDrawable(ColorDrawable(0))
            contentView = popView
            isFocusable = true
            width = window.decorView.width
            val rect = Rect()
            window.decorView.getWindowVisibleDisplayFrame(rect)
            val height = window.decorView.height - rect.bottom
            if (height > 0) {
                showAtLocation(window.decorView, Gravity.BOTTOM, 0, height)
            } else {
                dismiss()
            }

        }
        if (context.isDestroyed){

        }
        //传入activity根布局的root
        //val popView = LayoutInflater.from(context).inflate(layout, null)
        //val pop = PopupWindow(
        //    popView,
        //    LinearLayout.LayoutParams.MATCH_PARENT,
        //    200, true
        //)
        //contentView = popView
        //isFocusable = true
        //softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;

        //val inputMethodManager = ObtainApplication.app!!.getSystemService(
        //    Context.INPUT_METHOD_SERVICE
        //) as InputMethodManager
        //inputMethodManager.toggleSoftInput(1000, InputMethodManager.HIDE_NOT_ALWAYS);
        //showAtLocation(root, Gravity.BOTTOM, 0, 0)
    }
}