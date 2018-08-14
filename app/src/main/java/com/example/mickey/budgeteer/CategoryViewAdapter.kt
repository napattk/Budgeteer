package com.example.mickey.budgeteer

import android.content.Context
import android.support.v4.app.DialogFragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import kotlinx.android.synthetic.main.category_item_layout.view.*

class CategoryViewAdapter(context: Context, fragName: String,parentView: View, parentDialog: DialogFragment?): RecyclerView.Adapter<CustomCategoryViewHolder>(){

    var fragName: String? = null
    var incomeItems = arrayListOf("Salary", "Extra Income", "Gift", "Refund")
    var expenseItems = arrayListOf("Rent", "Food", "Entertainment","Transportation","Other Expense")
    var context: Context? = null
    var parentView: View? = null
    var parentDialog: DialogFragment? = null

    init {
        this.fragName = fragName
        this.context = context
        this.parentView = parentView
        this.parentDialog = parentDialog
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomCategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.category_item_layout, parent, false)
        return CustomCategoryViewHolder(cellForRow)

    }

    override fun getItemCount(): Int {
        when(fragName){
            "Expense" -> return expenseItems.size
            "Income" -> return incomeItems.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: CustomCategoryViewHolder, position: Int) {
        var categoryText: String? = null
        when(fragName){
            "Expense" -> {
                categoryText = expenseItems[position]
                when(position){
                    0 -> holder.itemView.categoryImageView.setImageResource(R.drawable.rent)
                    1 -> holder.itemView.categoryImageView.setImageResource(R.drawable.food)
                    2 -> holder.itemView.categoryImageView.setImageResource(R.drawable.entertainment)
                    3 -> holder.itemView.categoryImageView.setImageResource(R.drawable.transportation)
                    4 -> holder.itemView.categoryImageView.setImageResource(R.drawable.expense)
                }
            }
            "Income" -> {
                categoryText = incomeItems[position]
                when(position){
                    0 -> holder.itemView.categoryImageView.setImageResource(R.drawable.salary)
                    1 -> holder.itemView.categoryImageView.setImageResource(R.drawable.extra_income)
                    2 -> holder.itemView.categoryImageView.setImageResource(R.drawable.gift)
                    3 -> holder.itemView.categoryImageView.setImageResource(R.drawable.refund)

                }
            }
        }
        holder.itemView.categoryTextView.text = categoryText

        holder.parentLayout.setOnClickListener {
            (parentView as EditText).setText(categoryText);
            parentDialog!!.dismiss()
        }

    }

}

class CustomCategoryViewHolder(v: View): RecyclerView.ViewHolder(v){
    var parentLayout = v.categoryItem;

}