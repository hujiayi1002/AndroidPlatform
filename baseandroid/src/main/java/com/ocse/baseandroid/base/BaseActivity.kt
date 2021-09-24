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
import io.reactivex.disposables.CompositeDisposable
import kotlin.system.exitProcess


abstract class BaseActivity<V : ViewDataBinding>(getLayoutId: Int) :
    AppCompatActivity(getLayoutId) {
    private lateinit var viewModelProvider: ViewModelProvider
    private lateinit var loadingViewView: LoadingView
    private var hash: Int = 0
    private var lastClickTime: Long = 0
    private var spaceTime: Long = 2000
    val layout = getLayoutId
    lateinit var dataBinding: V
    private var mCompositeDisposable = CompositeDisposable()
    val mContext by lazy { this@BaseActivity }
    var isNeedDoubleExit = false
    private var exitTime = 0L
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
        loadingViewView = LoadingView.Builder(this).create()
        initTitleBar("")
        initView()
        initData()
    }

    private fun initTitleBar(title: String) {
        toolbar = findViewById(R.id.toolbar) ?: return
        setSupportActionBar(toolbar)
        toolbar.setContentInsetsRelative(0, 0)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        relBack = toolbar.findViewById(R.id.relBack)
        tvTitle = toolbar.findViewById(R.id.tvTitle)
        tvRight = toolbar.findViewById(R.id.tvRight)
        imgRight = toolbar.findViewById(R.id.imgRight)
        relBack.setOnClickListener { finish() }
        TitleBuilder(title).setRightImgGone().setRightTextGone()

    }

    open fun setMainTextView(title: String): TitleBuilder? {
        toolbar = findViewById(R.id.toolbar) ?: return null
        val titleBuilder = TitleBuilder(title)
        titleBuilder.setTitle()
        return titleBuilder
    }

    abstract fun initView()
    abstract fun initData()

    infix fun start(cla: Class<*>) {
        startActivity(Intent(mContext, cla))
    }
    infix fun start(intent: Intent) {
        startActivity(intent)
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


    //    @SuppressLint("CheckResult")
//    open fun getPermission() {
//        val rxPermissions = RxPermissions(this)
//        rxPermissions.request(
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.CAMERA,
//            Manifest.permission.ACCESS_COARSE_LOCATION,
//            Manifest.permission.ACCESS_FINE_LOCATION,
//            Manifest.permission.CONTROL_LOCATION_UPDATES,
//            Manifest.permission.RECORD_AUDIO
//        )?.subscribe { aBoolean ->
//            if (aBoolean) {
//            }
//        }
//    }
//
//    @SuppressLint("CheckResult")
//    open fun addPermission(vararg permissions: String) {
//        val rxPermissions = RxPermissions(mContext)
//        rxPermissions.request(
//            permissions.contentToString()
//        )?.subscribe { aBoolean ->
//            if (aBoolean) {
//            }
//        }
//    }

    override fun onBackPressed() {
        exit()
    }

    private fun exit() {
        if (isNeedDoubleExit) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                ToastUtil.show("再按一次退出程序")
                exitTime = System.currentTimeMillis()
            } else {
                finish()
                android.os.Process.killProcess(android.os.Process.myPid())
                exitProcess(0)
            }
        } else {
            finish()
        }
    }

    open fun setBarColor(color: Int, isDarkFont: Boolean) {
        ImmersionBar.with(this).statusBarColor(color).statusBarDarkFont(isDarkFont).init()

    }

    open fun transparentStatusBar(isDarkFont: Boolean) {
        ImmersionBar.with(this).transparentStatusBar().statusBarDarkFont(isDarkFont).init()
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
     * 初始化ViewModelProvider对象
     * @return
     */
    private fun getViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(this)
    }


    open fun loadingShow() {
        if (!loadingViewView.isShowing) {
            loadingViewView.show()
        }
    }

    open fun loadingDismiss() {
        loadingViewView.dismiss()
    }


    override fun onDestroy() {
        super.onDestroy()
        dataBinding.unbind()
        mCompositeDisposable.clear()
    }


    open inner class TitleBuilder(title: String) {
        private val myTitle = title
        open fun setTitle(): TitleBuilder {
            tvTitle.text = myTitle
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