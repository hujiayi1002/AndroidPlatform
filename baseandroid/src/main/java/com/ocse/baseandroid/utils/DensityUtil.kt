package com.ocse.baseandroid.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.WindowManager

object DensityUtil {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dp2px(dpValue: Float): Int {
        val scale =
            ObtainApplication.app!!.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dp(pxValue: Float): Int {
        val scale = if (ObtainApplication.app != null) {
            ObtainApplication.app!!.resources.displayMetrics.density
        } else {
            1f
        }

        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 获得屏幕高度
     *
     * @param
     * @return
     */
    val screenHeight: Int
        get() {
            val wm = ObtainApplication.app!!
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val outMetrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(outMetrics)
            return outMetrics.heightPixels
        }

    /**
     * 获得屏幕宽度
     *
     * @param
     * @return
     */
    val screenWidth: Int
        get() {
            val wm = ObtainApplication.app?.let {
                it.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            }
            val outMetrics = DisplayMetrics()
            wm?.defaultDisplay?.getMetrics(outMetrics)
            return outMetrics.widthPixels
        }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    fun snapShotWithoutStatusBar(activity: Activity): Bitmap? {
        val view = activity.window.decorView
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        val bmp = view.drawingCache
        val frame = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(frame)
        val statusBarHeight = frame.top
        val width = screenWidth
        val height = screenHeight
        var bp: Bitmap? = null
        bp = Bitmap.createBitmap(
            bmp, 0, statusBarHeight, width, height
                    - statusBarHeight
        )
        view.destroyDrawingCache()
        return bp
    }

    /**
     * 获得状态栏的高度
     *
     * @return
     */
    val statusHeight: Int
        get() {
            var statusHeight = -1
            try {
                val clazz = Class.forName("com.android.internal.R\$dimen")
                val `object` = clazz.newInstance()
                val height = clazz.getField("status_bar_height")?.get(`object`).toString().toInt()
                statusHeight =
                    ObtainApplication.app?.resources?.getDimensionPixelSize(height)!!
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return statusHeight
        }

}