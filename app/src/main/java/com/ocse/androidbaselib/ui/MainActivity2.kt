package com.ocse.androidbaselib.ui

import androidx.fragment.app.Fragment
import com.ocse.androidbaselib.R
import com.ocse.androidbaselib.fragment.HomeFragment
import com.ocse.androidbaselib.fragment.OtherFragment
import com.ocse.baseandroid.base.BaseMainActivity

class MainActivity2 : BaseMainActivity() {
    override fun initFragment(): Pair<Array<String>, ArrayList<Fragment>> {
        return Pair(
            arrayOf("1", "2", "3"), arrayListOf(
                HomeFragment.newInstance("1", "2"),
                OtherFragment.newInstance("1", "2"),
                HomeFragment.newInstance("1", "2")
            )
        )
    }

    override fun initNormalAndSelectIcon(): Pair<Array<Int>, Array<Int>> {
        return Pair(
            arrayOf(R.mipmap.en, R.mipmap.en, R.mipmap.en),
            arrayOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher)
        )
    }


    override fun initCustomBottomBarView() {
        bottomBarItemBuilder.titleSelectedColor(R.color.colorAccent)
    }

    override fun setTitleText(): String? {
        return "111"
    }
}