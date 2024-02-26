package com.ocse.baseandroid.base

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.iterator
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ImmersionBar
import com.ocse.baseandroid.R
import com.ocse.baseandroid.impl.saveAs
import com.ocse.baseandroid.impl.saveAsUnChecked
import com.ocse.baseandroid.utils.KeyBordStateUtil
import com.ocse.baseandroid.utils.ToastUtil
import com.ocse.baseandroid.view.LoadingView
import com.ocse.baseandroid.view.TitleBarView
import java.lang.reflect.ParameterizedType
import kotlin.system.exitProcess


abstract class BaseActivity<V : ViewBinding> : RootActivity() {
    private lateinit var viewModelProvider: ViewModelProvider
    open  lateinit var viewBinding: V
    val MAX_LENGTH = 8
    abstract fun setTitleText(): String?
    override fun initContent() {
        val type = javaClass.genericSuperclass
        val vbClass: Class<V> = type!!.saveAs<ParameterizedType>().actualTypeArguments[0].saveAs()
        val method = vbClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        viewBinding = method.invoke(this, layoutInflater)!!.saveAsUnChecked()
        setContentView(viewBinding.root)
        viewModelProvider = ViewModelProvider(this)
        initTitleBar(setTitleText())
        initView()
        initData()
    }

    abstract fun initView()
    abstract fun initData()
    private fun initTitleBar(title: String?) {
        for (view in (viewBinding.root as ViewGroup)) {
            if (view is TitleBarView) {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                view.post {
                    view.setTitle(sub(title))
                    view.setStatusColor(this, R.color.white, true)
                    view.relBack.setOnClickListener { finish() }
                }
            }
        }
    }

    private fun sub(title: String?): String? {
        return if (!title.isNullOrEmpty() && title.length > MAX_LENGTH) "${
            title.substring(
                0,
                MAX_LENGTH
            )
        }..." else title
    }

    /**
     * 创建ViewModel对象
     * @param clazz
     * @return
     */
    open fun <T : ViewModel> get(clazz: Class<T>): T {
        return viewModelProvider[clazz]
    }

    /**
     * 创建ViewModel对象
     * @param clazz
     * @return
     */
    fun <T : ViewModel> getViewModel(clazz: Class<T>): T {
        return viewModelProvider[clazz]
    }
}