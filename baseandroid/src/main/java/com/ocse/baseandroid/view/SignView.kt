package com.ocse.baseandroid.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.util.Base64
import android.view.MotionEvent
import android.view.View
import com.ocse.baseandroid.R
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * Created by designer on 17/8/16.
 */
class SignView : View {
    private var linePaint // 画笔
            : Paint? = null
    private var lines // 写字的笔迹，支持多笔画
            : ArrayList<Path>? = null
    private var lineCount // 记录笔画数目
            = 0
    private val DEFAULT_LINE_WIDTH = 10 // 默认笔画宽度
    private var lineColor = Color.BLACK // 默认字迹颜色（黑色）
    private var lineWidth = DEFAULT_LINE_WIDTH // 笔画宽度
        .toFloat()
    var isCanvacs = false
    var mCallBackListener: CallBackListener? = null

    constructor(context: Context?) : super(context) {
        //        mCallBackListener =(CallBackListener) context;
        init() // 普通初始化
        initLinePpaint()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(
                attrs,
                R.styleable.SignView
            )
            parseTyepdArray(a)
        }
        //        mCallBackListener =(CallBackListener) context;
        init() // 普通初始化
        initLinePpaint()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
//        mCallBackListener =(CallBackListener) context;
        if (attrs != null) {
            val a = context.obtainStyledAttributes(
                attrs,
                R.styleable.SignView
            )
            parseTyepdArray(a)
        }
        init() // 普通初始化
        initLinePpaint()
    }

    private fun setCallBackListener(listener: CallBackListener) {
        mCallBackListener = listener
    }

    private fun parseTyepdArray(a: TypedArray) {
        lineColor = a.getColor(R.styleable.SignView_lineColor, Color.RED)
        lineWidth = a.getDimension(R.styleable.SignView_lineWidth, 25f)
        a.recycle()
    }

    private fun init() {
        lines = ArrayList()
    }

    /**
     * 初始化画笔
     */
    private fun initLinePpaint() {
        linePaint = Paint()
        linePaint!!.color = lineColor // 画笔颜色
        linePaint!!.strokeWidth = lineWidth // 画笔宽度
        linePaint!!.strokeCap = Paint.Cap.ROUND // 设置笔迹的起始、结束为圆形
        linePaint!!.pathEffect =
            CornerPathEffect(50f) // PahtEfect指笔迹的风格，CornerPathEffect在拐角处添加弧度，弧度半径50像素点
        linePaint!!.style = Paint.Style.STROKE // 设置画笔风格
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        /**
         * 考虑到这个view会出现在lib工程里，因此使用if else
         */
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val path = Path()
                path.moveTo(event.x, event.y)
                lines!!.add(path)
                lineCount = lines!!.size
                //            isCanvacs=true;
    //            mCallBackListener.postView(isCanvacs);
            }
            MotionEvent.ACTION_MOVE -> {
                lines!![lineCount - 1].lineTo(event.x, event.y)
                invalidate()
                isCanvacs = true
                mCallBackListener!!.postView(isCanvacs)
            }
            else -> {
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (lines != null && lines!!.size > 0) {
            for (path in lines!!) {
                canvas.drawPath(path, linePaint!!)
            }
        }
    }
    // 开放设置画笔颜色和宽度的接口
    /**
     * 开放设置画笔颜色的接口
     *
     * @param lineColor
     */
    fun setLineColor(lineColor: Int) {
        this.lineColor = lineColor
        linePaint!!.color = lineColor
    }

    /**
     * 开放设置画笔宽度的接口
     *
     * @param lineWidth
     */
    fun setLineWidth(lineWidth: Float) {
        this.lineWidth = lineWidth
        linePaint!!.strokeWidth = lineWidth
    }

    /**
     * 清空输入
     */
    fun clearPath() {
        isCanvacs = false
        mCallBackListener!!.postView(isCanvacs)
        lines!!.removeAll(lines!!)
        invalidate()
    }

    /**
     * 将图片保存到文件
     */
    fun saveImageToFile(filePath: String): String {
        var path = ""
        return try {
            val file = File(filePath)
            if (!file.exists()) {
                file.mkdirs()
            }
            val localFile = file.absolutePath + "/" + System.currentTimeMillis() + ".jpg"
            val f = File(localFile)
            val fos = FileOutputStream(f)
            image.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.flush()
            fos.close()
            //            path[0]=encodeImage(localFile);
            path = localFile
            path
        } catch (e: IOException) {
            e.printStackTrace()
            path
        }
    }// 绘制背景
    // 绘制View视图内容
    /**
     * 将View保存为Bitmap
     */
    val image: Bitmap
        get() {
            val bitmap = Bitmap.createBitmap(
                width, height,
                Bitmap.Config.RGB_565
            )
            val canvas = Canvas(bitmap)
            // 绘制背景
            val bgDrawable = background
            if (bgDrawable != null) {
                bgDrawable.draw(canvas)
            } else {
                canvas.drawColor(Color.WHITE)
            }
            // 绘制View视图内容
            draw(canvas)
            return bitmap
        }

    private fun encodeImage(path: String): String {
        var encodeString: String? = ""
        //decode to bitmap
        val bitmap = BitmapFactory.decodeFile(path)
        //convert to byte array
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val bytes = baos.toByteArray()

        //base64 encode
        val encode = Base64.encode(bytes, Base64.DEFAULT)
        encodeString = String(encode)
        return encodeString
    }

    interface CallBackListener {
        fun postView(isCanvcs: Boolean)
    }
}