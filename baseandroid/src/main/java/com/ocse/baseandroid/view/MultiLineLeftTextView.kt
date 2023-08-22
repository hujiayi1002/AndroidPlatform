package com.ocse.baseandroid.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView

class MultiLineLeftTextView : AppCompatTextView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()
        gravity = if (lineCount > 1) { //得到文本的行数
            Gravity.LEFT or Gravity.CENTER_VERTICAL
        } else {
            Gravity.RIGHT or Gravity.CENTER_VERTICAL
        }
        canvas.restore()
    }
}