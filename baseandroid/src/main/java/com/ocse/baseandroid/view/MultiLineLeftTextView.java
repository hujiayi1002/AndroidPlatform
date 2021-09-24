package com.hnocse.xibeioilgashssemanagementsystem.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class MultiLineLeftTextView extends AppCompatTextView {


    public MultiLineLeftTextView(Context context) {
        super(context);
    }

    public MultiLineLeftTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MultiLineLeftTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        if (getLineCount() > 1) {//得到文本的行数
            setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
        } else {
            setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
        }
        canvas.restore();
    }

}
