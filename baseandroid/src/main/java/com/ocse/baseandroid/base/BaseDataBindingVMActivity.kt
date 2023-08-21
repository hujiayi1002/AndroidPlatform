package com.ocse.baseandroid.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gyf.immersionbar.ImmersionBar
import com.ocse.baseandroid.R
import com.ocse.baseandroid.utils.ToastUtil
import com.ocse.baseandroid.view.LoadingView
import java.lang.reflect.ParameterizedType
import kotlin.system.exitProcess


 abstract class BaseDataBindingVMActivity<V : ViewDataBinding, VM:BaseViewModel>(getLayoutId: Int) :
    RootActivity(getLayoutId) {
    private lateinit var viewModelProvider: ViewModelProvider
    private lateinit var loadingViewView: LoadingView
    val layout = getLayoutId
    lateinit var viewModel: VM
    lateinit var dataBinding: V
    private val mContext by lazy { this@BaseDataBindingVMActivity }
    private lateinit var relBack: RelativeLayout
    private lateinit var tvTitle: TextView
    private lateinit var tvRight: TextView
    private lateinit var imgRight: ImageView
    private lateinit var toolbar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transparentStatusBar(true)
        dataBinding = DataBindingUtil.setContentView(this, layout)
        viewModelProvider = getViewModelProvider()
        val argument = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments
        viewModel = ViewModelProvider(this)[argument[1] as Class<VM>]
        loadingViewView = LoadingView.Builder(this).create()
        initTitleBar(setTitleText())
        initView()
        initData()
    }

    private fun initTitleBar(title: String?) {
        toolbar = findViewById(R.id.toolbar) ?: return
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        relBack = toolbar.findViewById(R.id.relBack)
        tvTitle = toolbar.findViewById(R.id.tvTitle)
        tvRight = toolbar.findViewById(R.id.tvRight)
        imgRight = toolbar.findViewById(R.id.imgRight)
        relBack.setOnClickListener { finish() }
        val titleStr =
            if (!title.isNullOrEmpty() && title.length > 8) "${title.substring(0, 8)}..." else title
        toolbar.title = titleStr
    }
     abstract fun initView()
     abstract fun initData()
    /**
     * 设置标题
     */

    abstract fun setTitleText(): String?
    /**
     * 创建ViewModel对象
     * @param clazz
     * @return
     */
    @Deprecated("使用getViewModel代替")
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

    /**
     * 初始化ViewModelProvider对象
     * @return
     */
    private fun getViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        dataBinding.unbind()
    }

}