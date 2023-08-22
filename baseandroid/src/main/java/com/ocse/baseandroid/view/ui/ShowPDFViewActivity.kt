package com.ocse.baseandroid.view.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ImmersionBar
import com.ocse.baseandroid.R
import com.ocse.baseandroid.base.BaseActivity
import com.ocse.baseandroid.databinding.ActivityShowPdfviewBinding
import com.ocse.baseandroid.utils.DownLoadFileUtils
import com.ocse.baseandroid.utils.PermissionUtils
import com.ocse.baseandroid.utils.ToastUtil
import com.ocse.baseandroid.utils.WeakReferenceHandler
import com.ocse.baseandroid.view.LoadingView
import com.tencent.smtt.sdk.QbSdk
import java.io.File

class ShowPDFViewActivity : BaseActivity<ActivityShowPdfviewBinding>() {
    private var url = ""
    private var fileName = ""
    private lateinit var loadingView: LoadingView

    companion object {
        private const val Path = "url"
        private const val Name = "fileName"
        fun start(context: Context, name: String, path: String) {
            val intent = Intent(context, ShowPDFViewActivity::class.java)
            intent.putExtra(Path, path)
            intent.putExtra(Name, name)
            context.startActivity(intent)
        }

        fun startWPS() {

        }
    }

    override fun initView() {
        ImmersionBar.with(this).transparentStatusBar().statusBarDarkFont(true).init()
        getPermission()
        intent.getStringExtra(Path)?.let { url = it;downLoadFile() }
        loadingView = LoadingView.Builder(this).create()
        if (!loadingView.isShowing){
            loadingView.show()
        }

    }

    override fun initData() {
    }

    override fun setTitleText(): String? {
        var fileName = intent.getStringExtra(Name).toString()
        if (!fileName.isNullOrEmpty()) {
            if (fileName.length > 12) {
                fileName = fileName.substring(0, 11)
            }
        }
        return fileName
    }


    var mHandler: Handler = @SuppressLint("HandlerLeak")
    object : WeakReferenceHandler(this) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                10010 -> {
                    ToastUtil.show("下载失败")
                }
                6 -> {
                    if (::loadingView.isInitialized)
                        loadingView.dismiss()
                    displayFile(msg.obj as File)
                }
            }
            super.handleMessage(msg)
        }
    }

    private fun downLoadFile() {
        DownLoadFileUtils.download(url, fileName, object : DownLoadFileUtils.OnDownloadListener {
            override fun onDownloadSuccess(file: File) {
                //下载完成
                val message = Message()
                message.what = 6
                message.obj = file
                mHandler.sendMessage(message)
            }

            override fun onDownloading(progress: Int) {

            }

            override fun onDownloadFailed() {
                val message = Message()
                message.what = 10010
                mHandler.sendMessage(message)
            }

        })
    }

    private fun displayFile(file: File) {
        if (file.name.contains(".pdf", true)) {
            dataBinding.pdfView.fromFile(file).load()
        } else {
            QbSdk.openFileReader(this, file.path, null, null)
        }
    }

    private fun getPermission() {
        PermissionUtils.addPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
        )
    }

}