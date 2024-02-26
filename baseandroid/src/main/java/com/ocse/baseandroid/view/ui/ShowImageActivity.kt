package com.ocse.baseandroid.view.ui

import android.content.Context
import android.content.Intent
import com.gyf.immersionbar.ImmersionBar
import com.ocse.baseandroid.R
import com.ocse.baseandroid.base.BaseActivity
import com.ocse.baseandroid.databinding.ActivityShowImageBinding
import com.ocse.baseandroid.utils.GlideEngine


/**
 *   展示照片
 */
class ShowImageActivity : BaseActivity<ActivityShowImageBinding>() {
    private var path = ""
    private var name = ""

    companion object {
        const val Path = "path"
        const val Name = "name"
        fun start(context: Context, name: String, path: String) {
            val intent = Intent(context, ShowImageActivity::class.java)
            intent.putExtra(Path, path)
            intent.putExtra(Name, name)
            context.startActivity(intent)
        }
    }

    override fun initView() {
        ImmersionBar.with(this).statusBarColor(R.color.black).statusBarDarkFont(false).init()
    }

    override fun initData() {
        path =
            if (intent.getStringExtra(Path).isNullOrEmpty()) "" else intent.getStringExtra(Path)
                .toString()
        GlideEngine.instance.loadPhoto(path, viewBinding.photoView)
    }

    override fun setTitleText(): String {
        return if (intent.getStringExtra(Name).isNullOrEmpty()) "图片" else intent.getStringExtra(Name)
            .toString()
    }
}