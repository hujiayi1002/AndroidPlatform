package com.ocse.baseandroid.view.ui

import android.content.Context
import android.content.Intent
import com.ocse.baseandroid.R
import com.ocse.baseandroid.base.BaseActivity
import com.ocse.baseandroid.databinding.ActivityShowImageBinding
import com.ocse.baseandroid.utils.GlideEngine



class ShowImageActivity : BaseActivity<ActivityShowImageBinding>(R.layout.activity_show_image) {
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

    }

    override fun initData() {
        path =
            if (intent.getStringExtra(Path).isNullOrEmpty()) "" else intent.getStringExtra(Path)
                .toString()
        name =
            if (intent.getStringExtra(Name).isNullOrEmpty()) "图片" else intent.getStringExtra(Name)
                .toString()

        setMainTextView(name)
        GlideEngine.instance.loadPhoto(path, dataBinding.photoView)
    }
}