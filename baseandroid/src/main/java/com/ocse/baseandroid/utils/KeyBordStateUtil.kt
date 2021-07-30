package com.ocse.baseandroid.utils

import android.R
import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import kotlin.math.abs

/**
 * Function:
 * <br></br>
 * Describe:键盘的显示-关闭-以及键盘高度的监听 工具类
 * <br></br>
 * <br></br>
 */
class KeyBordStateUtil(context: Activity) {
    private var listener: OnKeyBordStateListener? = null
    private val rootLayout: View? = (context.findViewById<View>(R.id.content) as ViewGroup).getChildAt(
        0
    )
    private var mVisibleHeight = 0
    private var mFirstVisibleHeight = 0
    private val mOnGlobalLayoutListener: OnGlobalLayoutListener? =
        OnGlobalLayoutListener { calKeyBordState() }

    private fun calKeyBordState() {
        val r = Rect()
        rootLayout!!.getWindowVisibleDisplayFrame(r)
        val visibleHeight = r.height()
        if (mVisibleHeight == 0) {
            mVisibleHeight = visibleHeight
            mFirstVisibleHeight = visibleHeight
            return
        }
        if (mVisibleHeight == visibleHeight) {
            return
        }
        mVisibleHeight = visibleHeight
        val mIsKeyboardShow = mVisibleHeight < mFirstVisibleHeight
        if (mIsKeyboardShow) {
            //键盘高度
            val keyboardHeight = abs(mVisibleHeight - mFirstVisibleHeight)
            if (listener != null) {
                listener!!.onSoftKeyBoardShow(keyboardHeight)
            }
        } else {
            if (listener != null) {
                listener!!.onSoftKeyBoardHide()
            }
        }
    }

    fun addOnKeyBordStateListener(listener: OnKeyBordStateListener?) {
        this.listener = listener
    }

    fun removeOnKeyBordStateListener() {
        if (rootLayout != null && mOnGlobalLayoutListener != null) {
            rootLayout.viewTreeObserver.removeOnGlobalLayoutListener(mOnGlobalLayoutListener)
        }
        if (listener != null) {
            listener = null
        }
    }

    interface OnKeyBordStateListener {
        /**
         * 键盘显示
         *
         * @param keyboardHeight
         */
        fun onSoftKeyBoardShow(keyboardHeight: Int)

        /**
         * 键盘隐藏
         */
        fun onSoftKeyBoardHide()
    }

    init {
        rootLayout?.viewTreeObserver?.addOnGlobalLayoutListener(mOnGlobalLayoutListener)
    }
}