package com.example.mickey.budgeteer

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.category_item_layout.view.*

class CategoryViewAdapter(context: Context, fragName: String): RecyclerView.Adapter<CustomViewHolder>(){

    var fragName: String? = null
    var incomeItems = arrayListOf("Salary", "Extra Income", "Gift", "Refund")
    var expenseItems = arrayListOf("Rent", "Food", "Entertainment","Transportation","Other Expense")

    init {
        this.fragName = fragName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.category_item_layout, parent, false)
        return CustomViewHolder(cellForRow)

    }

    override fun getItemCount(): Int {
        when(fragName){
            "Expense" -> return expenseItems.size
            "Income" -> return incomeItems.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        //holder.itemView.categoryImageView =
        when(fragName){
            "Expense" -> {
                holder.itemView.categoryTextView.text = expenseItems[position]
                when(position){
                    0 -> holder.itemView.categoryImageView.setImageResource(R.drawable.rent)
                    1 -> holder.itemView.categoryImageView.setImageResource(R.drawable.food)
                    2 -> holder.itemView.categoryImageView.setImageResource(R.drawable.entertainment)
                    3 -> holder.itemView.categoryImageView.setImageResource(R.drawable.transportation)
                    4 -> holder.itemView.categoryImageView.setImageResource(R.drawable.expense)
                }
            }
            "Income" -> {
                holder.itemView.categoryTextView.text = incomeItems[position]
                when(position){
                    0 -> holder.itemView.categoryImageView.setImageResource(R.drawable.salary)
                    1 -> holder.itemView.categoryImageView.setImageResource(R.drawable.extra_income)
                    2 -> holder.itemView.categoryImageView.setImageResource(R.drawable.gift)
                    3 -> holder.itemView.categoryImageView.setImageResource(R.drawable.refund)

                }
            }
            }
    }

}


