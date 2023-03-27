package com.ocse.baseandroid.utils

import android.util.Log
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
     * @param url      下载连接
     * @param listener 下载监听
     */
    fun download(
        url: String?,
        bt: String,
        listener: OnDownloadListener
    ) {
        val request = Request.Builder().url(url!!).build()
        val okHttpClient = OkHttpClient()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // 下载失败
                listener.onDownloadFailed()
                MyLog.e("TAG", "onFailure: " + e.message)
            }

            @Throws(IOException::class)
            override fun onResponse(
                call: Call,
                response: Response
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
                    val file =
                        File(savePath, System.currentTimeMillis().toString() + bt)
                    fos = FileOutputStream(file)
                    var sum: Long = 0
                    while (`is`.read(buf).also { len = it } != -1) {
                        fos.write(buf, 0, len)
                        sum += len.toLong()
                        val progress = (sum * 1.0f / total * 100).toInt()
                        // 下载中
                        listener.onDownloading(progress)
                    }
                    fos.flush()
                    // 下载完成
                    listener.onDownloadSuccess(file)
                } catch (e: Exception) {
                    listener.onDownloadFailed()
                } finally {
                    try {
                        `is`?.close()
                        fos?.close()
                    } catch (e: IOException) {
                    }
                }
            }
        })
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