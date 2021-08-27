package com.ocse.baseandroid.view

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import com.tencent.smtt.export.external.interfaces.SslError
import com.tencent.smtt.export.external.interfaces.SslErrorHandler
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

/**
 * @author 11729
 */
class X5WebView : WebView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        this.webViewClient = client
        initWebViewSettings()
        this.view.isClickable = true
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        this.webViewClient = client
        initWebViewSettings()
        this.view.isClickable = true
    }

    constructor(
        context: Context,
        attributeSet: AttributeSet,
        defStyleAttr: Int,
        boolean: Boolean
    ) : super(
        context,
        attributeSet,
        defStyleAttr, boolean
    ) {
        this.webViewClient = client
        initWebViewSettings()
        this.view.isClickable = true
    }

    private val client: WebViewClient =
        object : WebViewClient() {
            /**
             * 防止加载网页时调起系统浏览器
             */
            override fun shouldOverrideUrlLoading(
                view: WebView,
                url: WebResourceRequest
            ): Boolean {
                //网页在webView中打开
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) { //安卓5.0的加载方法
                    view.loadUrl(url.toString())
                } else { //5.0以上的加载方法
                    view.loadUrl(url.url.toString())
                }
                return true
            }

            override fun onReceivedSslError(
                webView: WebView,
                sslErrorHandler: SslErrorHandler,
                sslError: SslError
            ) {
                sslErrorHandler.proceed()
                super.onReceivedSslError(webView, sslErrorHandler, sslError)
            }
        }

    private fun initWebViewSettings(): WebSettings {
        val webSetting = this.settings
        //禁用滑动按钮
        if (this.x5WebViewExtension != null) {
            this.x5WebViewExtension.isHorizontalScrollBarEnabled = false //水平不显示滚动按钮
            this.x5WebViewExtension.isVerticalScrollBarEnabled = false //垂直不显示滚动按钮
        }
        webSetting.javaScriptEnabled = true
        webSetting.javaScriptCanOpenWindowsAutomatically = true //设置允许js弹出alert对话框

        webSetting.allowFileAccess = true

        //        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//设置布局，会引起WebView的重新布局,默认值NARROW_COLUMNS

        webSetting.setSupportZoom(false) //进行控制缩放

        webSetting.builtInZoomControls = true //是否使用内置的缩放机制

        webSetting.useWideViewPort = true //将图片调整到适合webview的大小

        webSetting.setSupportMultipleWindows(true) //默认false false-WebView默人不支持新窗口

        webSetting.loadWithOverviewMode = true // 缩放至屏幕的大小

        webSetting.setAppCacheEnabled(true) //应用缓存API是否可用，默认值false, 结合setAppCachePath(String)使用。

        webSetting.defaultTextEncodingName = "UTF-8" //设置默认的字符编码集，默认”UTF-8”.

        // webSetting.setDatabaseEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.allowContentAccess = true
        webSetting.domStorageEnabled = true //DOM存储API是否可用，默认false。

        webSetting.loadsImagesAutomatically = true //WebView是否下载图片资源，默认为true

        webSetting.setGeolocationEnabled(true) //定位是否可用，默认为true

        webSetting.setAppCacheMaxSize(Long.MAX_VALUE) //设置应用缓存内容的最大值

//        val appCachePath: String = ObtainApplication().mContext.getCacheDir().getAbsolutePath()
//        webSetting.setAppCachePath(appCachePath) //设置应用缓存文件的路径

        webSetting.allowFileAccess = true //是否允许访问文件

        webSetting.userAgentString = null //设置应用缓存文件的路径置WebView的用户代理字符串。如果字符串为null或者empty，将使用系统默认值

        webSetting.pluginState = WebSettings.PluginState.ON_DEMAND //在API18以上已废弃。未来将不支持插件

        webSetting.cacheMode = WebSettings.LOAD_NO_CACHE //重写使用缓存的方式

        // 清除缓存和记录
        clearCache(true)
        clearHistory()
        //this.addJavascriptInterface(new JSScript(), "android");

        // this.getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension
        // settings 的设计
        return webSetting
    }

    init {

    }
}