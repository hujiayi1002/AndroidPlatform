package com.ocse.baseandroid.base

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.ocse.baseandroid.R
import com.ocse.baseandroid.databinding.FragmentWebBinding
import com.ocse.baseandroid.js.BaseJSScript
import com.ocse.baseandroid.utils.Logger
import com.ocse.baseandroid.view.LoadingView
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

/**
 *
 */
class BaseWebFragment : BaseFragment<FragmentWebBinding>(R.layout.fragment_web) {
    private var url = ""
    override fun onViewCreated(view: View) {
      val  loadingView = LoadingView.Builder(requireActivity()).create()
        if (!loadingView.isShowing&&isVisible)
            loadingView.show()
        dataBinding.x5Web.loadUrl(url)
        Logger.e("TAG", "onViewCreated: $url, ")
        dataBinding.x5Web.addJavascriptInterface(activity?.let { BaseJSScript() }, "android")
        dataBinding.x5Web.webViewClient = object : WebViewClient() {
            override fun onPageFinished(p0: WebView?, p1: String?) {
                super.onPageFinished(p0, p1)
                loadingView.dismiss()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(parm: String) =
            BaseWebFragment().apply {
                arguments = Bundle().apply {
                    url = parm
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            dataBinding.x5Web.let {
                (it.parent as ViewGroup).removeView(it)
                it.stopLoading()
                it.clearCache(true)
                it.settings.javaScriptEnabled = false
                it.clearHistory()
                it.removeAllViews()
                it.destroy()
            }
        } catch (e: Exception) {

        }
    }
}
