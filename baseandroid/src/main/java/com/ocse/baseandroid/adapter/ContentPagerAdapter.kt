package com.ocse.baseandroid.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ContentPagerAdapter(fm: FragmentManager, tabFragments: ArrayList<Fragment>) : FragmentStatePagerAdapter(
    fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    private var nameList: ArrayList<String> = ArrayList()
    var fragments = tabFragments

    public fun setNewData(nameList: ArrayList<String>) {
        this.nameList.clear()
        this.nameList = nameList
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }
    override fun getPageTitle(position: Int): CharSequence? {
//            var channel=myChannelList[position].
        return nameList[position]
    }
}