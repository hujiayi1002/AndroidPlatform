package com.ocse.baseandroid.view.ui

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import android.view.View
import com.gyf.immersionbar.ImmersionBar
import com.ocse.baseandroid.R
import com.ocse.baseandroid.base.BaseActivity
import com.ocse.baseandroid.databinding.ActivityShowVideoBinding
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import java.io.File

/**
 * GSYPlayer展示视频
 */
class ShowVideoActivity : BaseActivity<ActivityShowVideoBinding>() {
    private var orientationUtils: OrientationUtils? = null
    private var path = ""
    private var name = ""
    private var isLand = false

    companion object {
        private const val Path = "path"
        private const val Name = "name"
        fun start(context: Context, name: String, path: String) {
            val intent = Intent(context, ShowVideoActivity::class.java)
            intent.putExtra(Path, path)
            intent.putExtra(Name, name)
            context.startActivity(intent)
        }
    }

    override fun initView() {
        ImmersionBar.with(this).statusBarColor(R.color.black).statusBarDarkFont(false).init()
        path =
            if (intent.getStringExtra(Path).isNullOrEmpty()) "" else intent.getStringExtra(Path)
                .toString()
        name =
            if (intent.getStringExtra(Name).isNullOrEmpty()) "视频" else intent.getStringExtra(Name)
                .toString()
        if (path.startsWith("http")) {
            viewBinding.videoPlayer.setUp(path, true, name)
        } else {
            viewBinding.videoPlayer.setUp(Uri.fromFile(File(path)).toString(), false, name)
        }
        //增加title
        viewBinding.videoPlayer.titleTextView?.visibility = View.VISIBLE
        viewBinding.videoPlayer.titleTextView?.text = name
        //设置返回键
        viewBinding.videoPlayer.backButton?.visibility = View.VISIBLE
        //设置旋转
        orientationUtils = OrientationUtils(this, viewBinding.videoPlayer)
        viewBinding.videoPlayer.isAutoFullWithSize = true
        viewBinding.videoPlayer.isReleaseWhenLossAudio = true
        viewBinding.videoPlayer.backButton?.setOnClickListener {
            finish()
        }
        viewBinding.videoPlayer.fullscreenButton?.setOnClickListener {
            requestedOrientation = if (isLand) {
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            } else {
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
            isLand = !isLand
        }
        //是否可以滑动调整
        viewBinding.videoPlayer.setIsTouchWiget(true)

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        viewBinding.videoPlayer.onConfigurationChanged(this, newConfig, orientationUtils)
    }

    override fun initData() {
    }

    override fun setTitleText(): String? {
        return if (intent.getStringExtra(Name)
                .isNullOrEmpty()
        ) "视频" else intent.getStringExtra(Name)
            .toString()
    }

    fun initUrl() {
        path?.let {
            viewBinding.videoPlayer.setUp(path, true, name)
        }
    }

    fun initUri() {
        path?.let {
            val file =
                viewBinding.videoPlayer.setUp(Uri.fromFile(File(path)).toString(), false, name)
        }
    }

    override fun onPause() {
        super.onPause()
        viewBinding.videoPlayer.onVideoPause()
    }

    override fun onResume() {
        super.onResume()
        viewBinding.videoPlayer.onVideoResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
        if (orientationUtils != null) orientationUtils?.releaseListener()
    }

    override fun onBackPressed() {
        //先返回正常状态
        if (orientationUtils?.screenType === ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            viewBinding.videoPlayer.fullscreenButton?.performClick()
            return
        }
        //释放所有
        viewBinding.videoPlayer.setVideoAllCallBack(null)
        super.onBackPressed()
    }
}