package com.example.mickey.budgeteer

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View


@SuppressLint("ValidFragment")
class IncomeCategoryFragment(parentView: View?, parentDialog: DialogFragment?) : Fragment() {

    var parentView: View? = null
    var parentDialog: DialogFragment? = null
    init {
        this.parentView = parentView
        this.parentDialog = parentDialog
    }

    fun createInstance(): IncomeCategoryFragment {
        return IncomeCategoryFragment(parentView, parentDialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.category_fragment, container, false)
        val context = activity!!.applicationContext

        //Set-up category items list
        var recyclerView = view.findViewById<RecyclerView>(R.id.categoryRecyclerView)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = CategoryViewAdapter(context, "Income",parentView!!,parentDialog!!)

        return view

    }


}