package com.example.mickey.budgeteer

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v4.view.ViewPager
import android.support.design.widget.TabLayout




class CategoryPickerDialog : DialogFragment() {

    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootview = inflater.inflate(R.layout.category_picker_layout, container, false)

        tabLayout = rootview.findViewById(R.id.tabLayout)
        viewPager = rootview.findViewById(R.id.viewPager)

        val adapter = CategoryFragmentAdapter(getChildFragmentManager())
        adapter.addFragment("Income", IncomeCategoryFragment().createInstance())
        adapter.addFragment("Expense", ExpenseCategoryFragment().createInstance())

        viewPager!!.adapter = adapter
        tabLayout!!.setupWithViewPager(viewPager)

        return rootview
    }

}