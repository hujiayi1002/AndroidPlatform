package com.ocse.baseandroid.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.ocse.baseandroid.R
import com.ocse.baseandroid.impl.saveAs
import com.ocse.baseandroid.impl.saveAsUnChecked
import com.ocse.baseandroid.view.LoadingView
import java.lang.reflect.ParameterizedType

/**
 * BaseFragment，处理ViewModelProvider的初始化
 * */
abstract class BaseVMFragment<V : ViewBinding,VM:BaseViewModel> : Fragment() {
    private lateinit var loadingView: LoadingView
    private var hash: Int = 0
    private var lastClickTime: Long = 0
    private var spaceTime: Long = 2000
    lateinit var dataBinding: V
    private lateinit var relBack: RelativeLayout
    private lateinit var tvTitle: TextView
    private lateinit var tvRight: TextView
    private lateinit var imgRight: ImageView
    private lateinit var toolbar: Toolbar
    lateinit var viewModel: VM
    abstract fun onViewCreated(view: View)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val type = javaClass.genericSuperclass
        val vbClass: Class<V> = type!!.saveAs<ParameterizedType>().actualTypeArguments[0].saveAs()
        val method = vbClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        dataBinding = method.invoke(this, layoutInflater)!!.saveAsUnChecked()
        return dataBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val argument = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments
        viewModel = ViewModelProvider(this)[argument[1] as Class<VM>]
        loadingView = LoadingView.Builder(requireActivity()).create()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTitleBar("")
        onViewCreated(view)
    }

    private fun initTitleBar(title: String) {
        toolbar = dataBinding.root.findViewById(R.id.toolbar) ?: return
        toolbar.setContentInsetsRelative(0, 0)
        relBack = toolbar.findViewById(R.id.relBack)
        tvTitle = toolbar.findViewById(R.id.tvTitle)
        tvRight = toolbar.findViewById(R.id.tvRight)
        imgRight = toolbar.findViewById(R.id.imgRight)
        relBack.setOnClickListener { activity?.finish() }
        TitleBuilder(title).setRightTextGone().setRightImgGone().setLeftBackGone()

    }

    fun setMainTextView(title: String): TitleBuilder? {
        toolbar = dataBinding.root.findViewById(R.id.toolbar) ?: return null
        val titleBuilder = TitleBuilder(title)
        titleBuilder.setTitle()
        return titleBuilder
    }



    infix fun View.singleClick(clickAction: () -> Unit) {
        this.setOnClickListener {
            if (this.hashCode() != hash) {
                hash = this.hashCode()
                lastClickTime = System.currentTimeMillis()
                clickAction()
            } else {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastClickTime > spaceTime) {
                    lastClickTime = System.currentTimeMillis()
                    clickAction()
                }
            }
        }
    }

    infix fun start(cla: Class<*>) {
        startActivity(Intent(requireContext(), cla))
    }

    infix fun start(intent: Intent) {
        startActivity(intent)
    }

    open fun loadingShow() {
        if (!loadingView.isShowing) {
            loadingView.show()
        }
    }

    open fun loadingDismiss() {
        loadingView.dismiss()
    }


    inner class TitleBuilder(title: String) {
        private val myTitle = title

        fun setTitle(): TitleBuilder {
            tvTitle.text = myTitle
            return this
        }

        fun setLeftBackGone(): TitleBuilder {
            relBack.visibility = View.GONE
            return this
        }

        fun setLeftBackVisible(): TitleBuilder {
            relBack.visibility = View.VISIBLE
            return this
        }

        fun setOnBackPressed(onBackPressed: BackPressed): TitleBuilder {
            relBack.visibility = View.VISIBLE
            relBack.setOnClickListener {
                onBackPressed.onBackPressed()
            }
            return this
        }


        fun setRightTextGone(): TitleBuilder {
            tvRight.visibility = View.GONE
            return this
        }

        fun setRightText(text: String): TitleBuilder {
            tvRight.visibility = View.VISIBLE
            tvRight.text = text
            return this
        }

        fun setRightImgGone(): TitleBuilder {
            imgRight.visibility = View.GONE
            return this
        }

        fun setBackgroundColor(color: Int): TitleBuilder {
            if (::toolbar.isInitialized) {
                toolbar.setBackgroundColor(color)
            }
            return this
        }

        fun setRightImg(resource: Int): TitleBuilder {
            imgRight.visibility = View.VISIBLE
            imgRight.setImageResource(resource)
            return this
        }
    }

    interface BackPressed {
        fun onBackPressed()
    }
}