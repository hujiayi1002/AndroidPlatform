package com.ocse.baseandroid.view.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.gyf.immersionbar.ImmersionBar
import com.ocse.baseandroid.R
import com.ocse.baseandroid.databinding.ActivityWordViewBinding
import com.ocse.baseandroid.utils.DownLoadFileUtils
import com.ocse.baseandroid.utils.PermissionUtils
import com.ocse.baseandroid.utils.WeakReferenceHandler
import com.ocse.baseandroid.view.LoadingView
import com.tencent.smtt.sdk.QbSdk
import com.tencent.smtt.sdk.TbsReaderView
import okhttp3.*
import java.io.File


open class ShowWordViewActivity : AppCompatActivity(), TbsReaderView.ReaderCallback {
    private var url = ""
    private var fileName = ""
    private lateinit var loadingView: LoadingView
    private var mTbsReaderView: TbsReaderView? = null
    private lateinit var dataBinding: ActivityWordViewBinding

    companion object {
        const val Path = "fileName"
        const val Name = "url"
        fun start(context: Context, name: String, path: String) {
            val intent = Intent(context, ShowWordViewActivity::class.java)
            intent.putExtra(Path, path)
            intent.putExtra(Name, name)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_word_view)
        ImmersionBar.with(this).transparentStatusBar().statusBarDarkFont(true).init()
        intent.getStringExtra(Name)?.let { fileName = it }
        intent.getStringExtra(Path)?.let { url = it;downLoadFile() }
        mTbsReaderView = TbsReaderView(this@ShowWordViewActivity, this)
        dataBinding.layoutToolbar.tvTitle.text = "$fileName"
        dataBinding.layoutToolbar.relBack.setOnClickListener { finish() }
        getPermission()
        initView()
    }

    private fun initView() {
        loadingView = LoadingView.Builder(this).create()
        loadingView.setCanceledOnTouchOutside(true)
        if (!loadingView.isShowing) {
            loadingView.show()
        }
        dataBinding.wordView.addView(
            mTbsReaderView,
            RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        )
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
            }

        })
    }

    @SuppressLint("CheckResult")
    private fun getPermission() {
        PermissionUtils.addPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }


    var mHandler: Handler = @SuppressLint("HandlerLeak")
    object : WeakReferenceHandler(this) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                6 -> {
                    loadingView.dismiss()
                    displayFile(msg.obj as File)
                }
            }
            super.handleMessage(msg)
        }
    }


    private fun displayFile(file: File) {
        val bundle = Bundle()
        val path = file.absolutePath
        val name = path.substring(path.lastIndexOf(".") + 1)
        val tempPath = path.substring(0, path.lastIndexOf("/")) + "/temp." + name
        val renameFile = File(tempPath)
        if (renameFile.exists()) {
            renameFile.deleteOnExit()
        }
        file.renameTo(renameFile)
        val o = renameFile.exists()
        val l = renameFile.length()
        bundle.putString("filePath", renameFile.path)
        bundle.putString("tempPath", filesDir.path)

        val result =
            mTbsReaderView!!.preOpen(parseFormat(renameFile.name), false)
        if (result) {
            mTbsReaderView!!.openFile(bundle)
        } else {
            QbSdk.openFileReader(
                this, renameFile.path, null
            ) { s ->
                Log.e("TAG", "onReceiveValue:  $s")
                if (s == "fileReaderClosed") {
                    finish()
                } else if (s == "filepath error") {
                    dataBinding.tvNoType.visibility = View.VISIBLE
                    QbSdk.clearAllWebViewCache(this, true)
                }
            }
        }
    }

    private fun parseFormat(fileName: String): String? {
        return fileName.substring(fileName.lastIndexOf(".") + 1)
    }

    override fun onCallBackAction(p0: Int?, p1: Any?, p2: Any?) {
    }

    override fun onDestroy() {
        super.onDestroy()
        mTbsReaderView!!.onStop()
    }
}