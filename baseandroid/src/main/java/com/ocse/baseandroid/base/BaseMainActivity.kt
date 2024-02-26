package com.ocse.baseandroid.base

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.view.iterator
import androidx.fragment.app.Fragment
import com.chaychan.library.BottomBarItem
import com.ocse.baseandroid.R
import com.ocse.baseandroid.adapter.BaseViewPagerAdapter
import com.ocse.baseandroid.databinding.ActivityBaseMainBinding
import com.ocse.baseandroid.utils.DensityUtil
import com.ocse.baseandroid.view.TitleBarView


abstract class BaseMainActivity : RootActivity() {
    private lateinit var viewBinding: ActivityBaseMainBinding
    open lateinit var bottomBarItemBuilder: BottomBarItem.Builder
    override fun initContent() {
        viewBinding = ActivityBaseMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        bottomBarItemBuilder = BottomBarItem.Builder(this)
        bottomBarItemBuilder.iconHeight(DensityUtil.dp2px(40f))
        bottomBarItemBuilder.iconWidth(DensityUtil.dp2px(40f))
        initTitleBar(setTitleText())
        initCustomBottomBarView()
        initDefaultBottomView()
    }

    /**
     * 标题和图片
     * titleArray,
     */
    abstract fun initFragment(): Pair<Array<String>, ArrayList<Fragment>>

    /*
     * 标题和图片
     * normalIcon, selectedIcon
     */
    abstract fun initNormalAndSelectIcon(): Pair<Array<Int>, Array<Int>>
    abstract fun initCustomBottomBarView()

    abstract fun setTitleText(): String?

    private fun initDefaultBottomView() {
        val (titleArray, fragmentList) = initFragment()
        val (normalIcon, selectedIcon) = initNormalAndSelectIcon()
        titleArray.forEachIndexed { index, title ->
            val create = bottomBarItemBuilder.create(
                ContextCompat.getDrawable(
                    this,
                    normalIcon[index]
                ),
                ContextCompat.getDrawable(this, selectedIcon[index]), title
            )
            viewBinding.bottomBar.addItem(create)
        }
        viewBinding.viewPager.adapter = BaseViewPagerAdapter(supportFragmentManager, fragmentList)
        viewBinding.bottomBar.setViewPager(viewBinding.viewPager)
    }

    private fun initTitleBar(title: String?) {
        for (view in (viewBinding.root)) {
            if (view is TitleBarView) {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                view.post {
                    view.setTitle(title)
                    view.setStatusColor(this, R.color.white, true)
                    view.relBack.setOnClickListener { finish() }
                }
            }
        }
    }
}