package com.ocse.baseandroid.base

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.KeyEvent
import androidx.core.content.FileProvider
import com.huantansheng.easyphotos.EasyPhotos
import com.huantansheng.easyphotos.callback.SelectCallback
import com.huantansheng.easyphotos.models.album.entity.Photo
import com.ocse.baseandroid.R
import com.ocse.baseandroid.databinding.ActivityBaseWebBinding
import com.ocse.baseandroid.js.BaseJSScript
import com.ocse.baseandroid.utils.GlideEngine
import com.ocse.baseandroid.utils.ObtainApplication
import com.ocse.baseandroid.view.ChooseTakeBottomSheetDialog
import com.tencent.smtt.sdk.ValueCallback
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File
import java.util.*


abstract class BaseWebActivity : BaseActivity<ActivityBaseWebBinding>(R.layout.activity_base_web) {
    private var url = ""
    private var path = ""
    private var uploadFiles: ValueCallback<Array<Uri>>? = null
    abstract fun setUrl(): String
    override fun initView() {
        url = setUrl()
        dataBinding.x5Web.addJavascriptInterface(BaseJSScript(), "android")
        loadingShow()
        dataBinding.x5Web.webViewClient = object : WebViewClient() {
            override fun onPageFinished(p0: WebView?, p1: String) {
                super.onPageFinished(p0, p1)
                url = p1
                loadingDismiss()
            }
        }
        dataBinding.x5Web.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(p0: WebView?, p1: String?) {
                super.onReceivedTitle(p0, p1)
                dataBinding.toolbar.tvTitle.text = p1
            }

            // For Android  >= 5.0
            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                uploadFiles = filePathCallback
                val bottomSheetDialog = ChooseTakeBottomSheetDialog(this@BaseWebActivity)
                bottomSheetDialog.show(supportFragmentManager, "")
                bottomSheetDialog.setTakePop(object : ChooseTakeBottomSheetDialog.ChooseTake {
                    override fun album() {
                        openFileChooseProcess()
                    }

                    override fun take() {
                        openTakePhotoChooseProcess()
                    }

                    override fun dismiss() {
                        uploadFiles?.onReceiveValue(null)
                    }
                })
                return true
            }
        }
    }

    override fun initData() {

    }

    private fun openFileChooseProcess() {
        EasyPhotos.createAlbum(this@BaseWebActivity, false, true, GlideEngine.instance)
            .start(object : SelectCallback() {
                override fun onResult(photos: ArrayList<Photo>, isOriginal: Boolean) {
                    onReceiveValue(photos[0].path)
                }

                /**
                 * 什么都没选，取消选择回调
                 */
                override fun onCancel() {
                }
            })

    }

    private fun openTakePhotoChooseProcess() {
        EasyPhotos.createCamera(this, true)//参数说明：上下文
            .setFileProviderAuthority("${ObtainApplication.getApp().packageName}.fileprovider")//参数说明：见下方`FileProvider的配置`
            .start(object : SelectCallback() {
                override fun onResult(photos: ArrayList<Photo>, isOriginal: Boolean) {
                    onReceiveValue(photos[0].path)
                }

                /**
                 * 什么都没选，取消选择回调
                 */
                override fun onCancel() {
                }
            })
    }

    private fun onReceiveValue(originalUri: String?) {
        val file = File(originalUri)
        if (!file.parentFile.exists()) file.parentFile.mkdirs()
        Luban.with(this)
            .load(file).ignoreBy(1024).setCompressListener(object : OnCompressListener {
                override fun onStart() {
                }

                override fun onSuccess(file: File) {
                    val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        FileProvider.getUriForFile(
                            this@BaseWebActivity,
                            "${ObtainApplication.getApp().packageName}.fileprovider",
                            file
                        )
                    } else {
                        Uri.fromFile(file)
                    }
                    if (uri != null) {
                        uploadFiles?.onReceiveValue(arrayOf(uri))
                    }
                    uploadFiles = null
                }

                override fun onError(e: Throwable?) {
                }

            }).launch()
    }

    override fun onActivityResult(
        requestCode: Int, resultCode: Int, data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_CANCELED) {
            if (null != uploadFiles) {
                uploadFiles?.onReceiveValue(null)
                dataBinding.x5Web.loadUrl(url)
                uploadFiles = null
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (dataBinding.x5Web.canGoBack()!!) {
                dataBinding.x5Web.goBack()
            } else {
                finish()
            }
            return false
        }
        return super.onKeyDown(keyCode, event)
    }

}