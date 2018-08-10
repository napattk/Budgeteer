package com.example.mickey.budgeteer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.budget_item_layout.view.*

class BudgetViewAdapter: RecyclerView.Adapter<CustomViewHolder>(){

    //Number of items in list
    override fun getItemCount(): Int {
        return 3;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.budget_item_layout, parent, false);
        return CustomViewHolder(cellForRow);
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder?.itemView.budgetItemTitle?.text = "123";
        holder.itemView.budgetItemSubText.text = "1234";

        //holder?.itemView.budgetItemImage.setImageResource();
    }
}

class CustomViewHolder(v: View): RecyclerView.ViewHolder(v){


}