package com.example.mickey.budgeteer

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter


class CategoryFragmentAdapter(fragmantManager: FragmentManager) : FragmentPagerAdapter(fragmantManager) {
    internal var fragmentList: MutableList<Fragment> = ArrayList()
    internal var fragmentTitleList: MutableList<String> = ArrayList()


    fun addFragment(title: String, fragment: Fragment) {
        fragmentTitleList.add(title)
        fragmentList.add(fragment)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return fragmentTitleList[position]
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }
}