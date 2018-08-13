package com.example.mickey.budgeteer

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View


class ExpenseCategoryFragment() : Fragment() {

    fun createInstance(): ExpenseCategoryFragment {
        return ExpenseCategoryFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.category_fragment, container, false)
        val context = activity!!.applicationContext

        //Set-up category items list
        var recyclerView = view.findViewById<RecyclerView>(R.id.categoryRecyclerView)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = CategoryViewAdapter(context, "Expense")

        return view

    }


}