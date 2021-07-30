package com.ocse.baseandroid.utils

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.Html
import android.text.Html.ImageGetter
import android.text.Html.TagHandler
import android.text.Spanned
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ImageSpan
import android.view.View
import android.widget.TextView
import org.xml.sax.XMLReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class HtmlFromUtils {
    /**
     * 此类负责处理source字符串中的img标签 对其添加点击事件
     */
    private class URLTagHandler : TagHandler {
        override fun handleTag(
            opening: Boolean,
            tag: String,
            output: Editable,
            xmlReader: XMLReader
        ) {
            // 处理标签<img>
            if (tag.toLowerCase(Locale.getDefault()) == "img") {
                // 获取长度
                val len = output.length
                // 获取图片地址
                val images =
                    output.getSpans(len - 1, len, ImageSpan::class.java)
                val imgURL = images[0].source
                // 使图片可点击并监听点击事件
                output.setSpan(
                    ClickableImage(ObtainApplication.getApp(), imgURL),
                    len - 1,
                    len,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        private inner class ClickableImage(
            private val context: Context,
            private val url: String?
        ) : ClickableSpan() {
            override fun onClick(widget: View) {
                // 进行图片点击之后的处理
//                Toast.makeText(context, "点击图片的地址" + url, Toast.LENGTH_LONG).show();
                clickImg!!.onClickImg(url)
            }

        }

    }

//    fun setImgOnclick(clickImg: ClickImgImpl) {
//        this.clickImg = clickImg
//    }

    interface ClickImgImpl {
        fun onClickImg(url: String?)
    }

    companion object {
        /**
         * 网络请求获取图片
         */
        private fun getImageFromNetwork(imageUrl: String): Drawable? {
            var myFileUrl: URL? = null
            var drawable: Drawable? = null
            try {
                myFileUrl = URL(imageUrl)
                val conn = myFileUrl
                    .openConnection() as HttpURLConnection
                conn.doInput = true
                conn.connect()
                val `is` = conn.inputStream
                drawable = Drawable.createFromStream(`is`, null)
                `is`.close()
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
            return drawable
        }

        private var drawable: Drawable? = null

        /**
         * 将html字符串中的图片加载出来 设置点击事件 然后TextView进行显示
         *
         * @param context
         * @param v
         * @param sources
         */
        fun setTextFromHtml(
            context: Activity?,
            v: TextView?,
            sources: String?
        ) {
            if (TextUtils.isEmpty(sources) || context == null || v == null) return
            synchronized(HtmlFromUtils::class.java) {
                //同步锁
                v.movementMethod =
                    LinkMovementMethod.getInstance() //如果想对img标签添加点击事件必须调用这句 使图片可以获取焦点
                v.text = Html.fromHtml(sources) //默认不处理图片先这样简单设置
                Thread(Runnable
                //开启线程加载其中的图片
                {
                    val imageGetter = ImageGetter { source ->
                        //Html.fromhtml方法中有一个参数 就是ImageGetter 此类负责加载source中的图片
                        //                            source = "http://www.dujiaoshou.com/" + source;//source就是img标签中src属性值，相对路径的此处可以对其进行处理添加头部
                        drawable =
                            getImageFromNetwork(source)
                        if (drawable != null) {
                            var w =
                                drawable!!.intrinsicWidth
                            var h =
                                drawable!!.intrinsicHeight
                            //对图片大小进行等比例放大 此处宽高可自行调整
                            if (w < h && h > 0) {
                                val scale = 400.0f / h
                                w = (scale * w).toInt()
                                h = (scale * h).toInt()
                            } else if (w > h && w > 0) {
                                val scale = 1000.0f / w
                                w = (scale * w).toInt()
                                h = (scale * h).toInt()
                            }
                            drawable!!.setBounds(0, 0, w, h)
                        } else if (drawable == null) {
                            //bindData();
                            return@ImageGetter null
                        }
                        drawable
                    }
                    //第三个参数 new URLTagHandler(context)负责添加img标签的点击事件
                    val charSequence: CharSequence =
                        Html.fromHtml(sources, imageGetter, URLTagHandler())
                    //在activiy的runOnUiThread方法中更新ui
                    context.runOnUiThread(Runnable { v.text = charSequence })
                }).start()
            }
        }

        var clickImg: ClickImgImpl? = null
    }
}