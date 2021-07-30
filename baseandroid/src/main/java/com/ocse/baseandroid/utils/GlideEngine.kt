package com.ocse.baseandroid.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.huantansheng.easyphotos.engine.ImageEngine

class GlideEngine  //单例模式，私有构造方法
private constructor() : ImageEngine {
    /**
     * 加载图片到ImageView
     *
     * @param context   上下文
     * @param uri       图片路径
     * @param imageView 加载到的ImageView
     */
    override fun loadPhoto(context: Context, uri: Uri, imageView: ImageView) {
        Glide.with(context).load(uri).into(imageView)
    }

    fun loadPhoto(path: Any?, imageView: ImageView) {
        Glide.with(imageView.context).load(path).into(imageView)
    }

    fun loadPhotoCircle(path: Any?, roundingRadius: Int, imageView: ImageView) {
        if (roundingRadius > 0) {
            //设置图片圆角角度
            val roundedCorners = RoundedCorners(roundingRadius)
            //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
            val options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
            Glide.with(imageView.context).load(path).apply(options).into(imageView)
        } else {
            loadPhoto(path, imageView)
        }
    }

    /**
     * 加载gif动图图片到ImageView，gif动图不动
     *
     * @param context   上下文
     * @param gifPath   gif动图路径
     * @param imageView 加载到的ImageView
     *
     *
     * 备注：不支持动图显示的情况下可以不写
     */
    override fun loadGifAsBitmap(context: Context, gifPath: Uri, imageView: ImageView) {
        Glide.with(context).asBitmap().load(gifPath).into(imageView)
    }

    /**
     * 加载gif动图到ImageView，gif动图动
     *
     * @param context   上下文
     * @param uri       gif动图路径
     * @param imageView 加载动图的ImageView
     *
     *
     * 备注：不支持动图显示的情况下可以不写
     */
    override fun loadGif(context: Context, uri: Uri, imageView: ImageView) {
        Glide.with(context).asGif().load(uri).into(imageView)
    }

    /**
     * 获取图片加载框架中的缓存Bitmap
     *
     * @param context 上下文
     * @param uri     图片路径
     * @param width   图片宽度
     * @param height  图片高度
     * @return Bitmap
     * @throws Exception 异常直接抛出，EasyPhotos内部处理
     */
    @Throws(Exception::class)
    override fun getCacheBitmap(context: Context, uri: Uri, width: Int, height: Int): Bitmap {
        return Glide.with(context).asBitmap().load(uri).submit(width, height).get()
    }

    companion object {
        //获取单例
        //单例
        val instance by lazy { GlideEngine() }
    }
}