package com.ocse.baseandroid.utils

import okhttp3.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * @author hujiayi
 */
object DownLoadFileUtils {
    /**
     *
     * @param url String? 下载url
     * @param btType String 标题+类型
     * @param isAppendSysTime Boolean 是否在文件名拼接时间戳
     * @param listener OnDownloadListener 回调
     * @throws IOException
     */
    @JvmStatic
    fun download(
        url: String,
        btType: String,
        isAppendSysTime: Boolean,
        listener: OnDownloadListener?,
    ) {
        val request = Request.Builder().url(url).build()
        val okHttpClient = OkHttpClient()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // 下载失败
                listener?.onDownloadFailed()
                MyLog.e("TAG", "onFailure: " + e.message)
            }

            @Throws(IOException::class)
            override fun onResponse(
                call: Call,
                response: Response,
            ) {
                var `is`: InputStream? = null
                var fos: FileOutputStream? = null
                val buf = ByteArray(2048)
                var len = 0
                // 储存下载文件的目录
                val savePath = ObtainApplication.app!!.getExternalFilesDir("file")
                try {
                    `is` = response.body!!.byteStream()
                    val total = response.body!!.contentLength()
                    val file = if (isAppendSysTime) File(
                        savePath,
                        System.currentTimeMillis().toString() + btType
                    ) else File(savePath, btType)
                    fos = FileOutputStream(file)
                    var sum: Long = 0
                    while (`is`.read(buf).also { len = it } != -1) {
                        fos.write(buf, 0, len)
                        sum += len.toLong()
                        val progress = (sum * 1.0f / total * 100).toInt()
                        // 下载中
                        listener?.onDownloading(progress)
                    }
                    fos.flush()
                    // 下载完成
                    listener?.onDownloadSuccess(file)
                } catch (e: Exception) {
                    listener?.onDownloadFailed()
                } finally {
                    try {
                        `is`?.close()
                        fos?.close()
                    } catch (_: IOException) {
                    }
                }
            }
        })
    }

    /**
     * @param url  下载连接 类型默认png
     */
    fun download(
        url: String,
    ) {
        download(url, ".png", true, null)
    }

    /**
     * @param url  下载连接
     * @param btType  标题+类型
     */
    fun download(
        url: String,
        btType: String,
    ) {
        download(url, btType, false, null)
    }

    /**
     * @param url  下载连接
     * @param listener 下载监听
     * @param btType 标题+类型
     */
    fun download(
        url: String,
        btType: String,
        listener: OnDownloadListener,
    ) {
        download(url, btType, false, listener)
    }

    interface OnDownloadListener {
        /**
         * 下载成功
         */
        fun onDownloadSuccess(file: File)

        /**
         * @param progress 下载进度
         */
        fun onDownloading(progress: Int)

        /**
         * 下载失败
         */
        fun onDownloadFailed()
    }
}