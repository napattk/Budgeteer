package com.example.mickey.budgeteer

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v4.view.ViewPager
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentManager


@SuppressLint("ValidFragment")
class CategoryPickerDialog(parentView: View?) : DialogFragment() {

    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    var parentView: View? = null

    init {
        this.parentView = parentView
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootview = inflater.inflate(R.layout.category_picker_layout, container, false)

        tabLayout = rootview.findViewById(R.id.tabLayout)
        viewPager = rootview.findViewById(R.id.viewPager)

        val adapter = CategoryFragmentAdapter(getChildFragmentManager())
        adapter.addFragment("Income", IncomeCategoryFragment(parentView,this).createInstance())
        adapter.addFragment("Expense", ExpenseCategoryFragment(parentView,this).createInstance())

        viewPager!!.adapter = adapter
        tabLayout!!.setupWithViewPager(viewPager)

        return rootview
    }



}