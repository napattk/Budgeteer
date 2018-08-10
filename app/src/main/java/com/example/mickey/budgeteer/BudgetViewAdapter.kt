package com.example.mickey.budgeteer

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.budget_item_layout.view.*

class BudgetViewAdapter(context: Context): RecyclerView.Adapter<CustomViewHolder>(){

    var budgetItems: MutableList<Budget>? = null;

    init {
        var dbHandler = DBHandler(context)
        budgetItems = dbHandler.readData()

    }

    override fun getItemCount(): Int {//Number of items in list
        return budgetItems!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.budget_item_layout, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        holder?.itemView.budgetItemTitle?.text = budgetItems!![position].title
        holder.itemView.budgetItemAmount.text = budgetItems!![position].amount.toString()

        var type = budgetItems!![position].type
        println(type)
        when(type){
            "Income" -> holder?.itemView.budgetItemImage.setImageResource(R.drawable.income)
            "Expense" -> holder?.itemView.budgetItemImage.setImageResource(R.drawable.expense)
        }


    }
}

class CustomViewHolder(v: View): RecyclerView.ViewHolder(v){


}