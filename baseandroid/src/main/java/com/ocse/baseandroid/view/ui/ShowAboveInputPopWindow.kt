package com.ocse.baseandroid.view.ui

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.PopupWindow
import com.ocse.baseandroid.utils.ObtainApplication

class ShowAboveInputPopWindow:PopupWindow {
    /**
     * root:  activity根布局的root
     * layout: ShowAboveInputPopWindow的布局
     */
    constructor(context: Context,layout:Int,root:View) :super(context){
        //传入activity根布局的root
        val popView = LayoutInflater.from(context).inflate(layout,null)
        //val pop = PopupWindow(
        //    popView,
        //    LinearLayout.LayoutParams.MATCH_PARENT,
        //    200, true
        //)
         contentView=popView
         isFocusable = true;
         softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;

        val inputMethodManager = ObtainApplication.app!!.getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.toggleSoftInput(1000, InputMethodManager.HIDE_NOT_ALWAYS);
        showAtLocation(root, Gravity.BOTTOM,0,0)
    }
}