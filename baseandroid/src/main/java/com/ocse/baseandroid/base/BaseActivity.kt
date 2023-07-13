package com.ocse.baseandroid.base

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
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
import androidx.lifecycle.get
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ImmersionBar
import com.ocse.baseandroid.R
import com.ocse.baseandroid.impl.saveAs
import com.ocse.baseandroid.impl.saveAsUnChecked
import com.ocse.baseandroid.utils.ToastUtil
import com.ocse.baseandroid.view.LoadingView
import com.ocse.baseandroid.view.TitleBarView
import java.lang.reflect.ParameterizedType
import kotlin.system.exitProcess


abstract class BaseActivity<V : ViewBinding> : RootActivity() {
    private lateinit var viewModelProvider: ViewModelProvider
    lateinit var dataBinding: V
    private lateinit var relBack: RelativeLayout
    private lateinit var tvTitle: TextView
    private lateinit var tvRight: TextView
    private lateinit var imgRight: ImageView
    private lateinit var toolbar: Toolbar
    override fun initContent() {
        val type = javaClass.genericSuperclass
        val vbClass: Class<V> = type!!.saveAs<ParameterizedType>().actualTypeArguments[0].saveAs()
        val method = vbClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        dataBinding = method.invoke(this, layoutInflater)!!.saveAsUnChecked()
        setContentView(dataBinding.root)
        viewModelProvider = ViewModelProvider(this)
        initTitleBar(setTitleText())
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
        //toolbar.title = titleStr
        Log.e("TAG", "initTitleBar: ${titleStr} ")
        toolbar.post {
            TitleBuilder().setRightImgGone().setRightTextGone().setTitle(titleStr)
        }
    }


    abstract fun setTitleText(): String?

    /**
     * 创建ViewModel对象
     * @param clazz
     * @return
     */
    @Deprecated("getViewModel")
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

    open inner class TitleBuilder {
        open fun setTitle(title: String?): TitleBuilder {
            tvTitle.text = title
            return this
        }

        open fun setLeftBackGone(): TitleBuilder {
            relBack.visibility = View.GONE
            return this
        }

        open fun setLeftBackVisible(): TitleBuilder {
            relBack.visibility = View.VISIBLE
            return this
        }

        open fun getBack(): View {
            relBack.visibility = View.VISIBLE
            return relBack
        }

        open fun getRight(): View {
            tvRight.visibility = View.VISIBLE
            return tvRight
        }

        open fun setRightTextGone(): TitleBuilder {
            tvRight.visibility = View.GONE
            return this
        }

        open fun setRightText(text: String): TitleBuilder {
            tvRight.visibility = View.VISIBLE
            tvRight.text = text
            return this
        }

        open fun setRightImgGone(): TitleBuilder {
            imgRight.visibility = View.GONE
            return this
        }

        open fun setRightImg(resource: Int): TitleBuilder {
            imgRight.visibility = View.VISIBLE
            imgRight.setImageResource(resource)
            return this
        }

        open fun setBackgroundColor(color: Int): TitleBuilder {
            if (::toolbar.isInitialized) {
                toolbar.setBackgroundColor(color)
            }
            return this
        }
    }
}