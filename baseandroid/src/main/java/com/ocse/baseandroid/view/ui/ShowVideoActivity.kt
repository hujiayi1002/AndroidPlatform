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
import com.ocse.baseandroid.utils.ToastUtil
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
            dataBinding.videoPlayer.setUp(path, true, name)
        } else {
            dataBinding.videoPlayer.setUp(Uri.fromFile(File(path)).toString(), false, name)
        }
        //增加title
        dataBinding.videoPlayer.titleTextView?.visibility = View.VISIBLE
        dataBinding.videoPlayer.titleTextView?.text = name
        //设置返回键
        dataBinding.videoPlayer.backButton?.visibility = View.VISIBLE
        //设置旋转
        orientationUtils = OrientationUtils(this, dataBinding.videoPlayer)
        dataBinding.videoPlayer.isAutoFullWithSize = true
        dataBinding.videoPlayer.isReleaseWhenLossAudio = true
        dataBinding.videoPlayer.backButton?.setOnClickListener {
            finish()
        }
        dataBinding.videoPlayer.fullscreenButton?.setOnClickListener {
            requestedOrientation = if (isLand) {
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }else{
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
            isLand=!isLand
        }
        //是否可以滑动调整
        dataBinding.videoPlayer.setIsTouchWiget(true)

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        dataBinding.videoPlayer.onConfigurationChanged(this, newConfig, orientationUtils)
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
            dataBinding.videoPlayer.setUp(path, true, name)
        }
    }

    fun initUri() {
        path?.let {
            val file =
                dataBinding.videoPlayer.setUp(Uri.fromFile(File(path)).toString(), false, name)
        }
    }

    override fun onPause() {
        super.onPause()
        dataBinding.videoPlayer.onVideoPause()
    }

    override fun onResume() {
        super.onResume()
        dataBinding.videoPlayer.onVideoResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
        if (orientationUtils != null) orientationUtils?.releaseListener()
    }

    override fun onBackPressed() {
        //先返回正常状态
        if (orientationUtils?.screenType === ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            dataBinding.videoPlayer.fullscreenButton?.performClick()
            return
        }
        //释放所有
        dataBinding.videoPlayer.setVideoAllCallBack(null)
        super.onBackPressed()
    }
}