package com.example.mickey.budgeteer

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.budget_item_layout.view.*
import kotlinx.android.synthetic.main.category_item_layout.view.*
import java.text.SimpleDateFormat


class BudgetViewAdapter(context: Context, budgetItems: MutableList<Budget>): RecyclerView.Adapter<CustomBudgetViewHolder>(){

    var budgetItems: MutableList<Budget>? = null;
    var dateFormat = SimpleDateFormat("dd MMM yyyy")
    var context: Context? = null

    init {
        this.budgetItems = budgetItems
        this.context = context
    }

    override fun getItemCount(): Int {//Number of items in list
        if(budgetItems == null){
            return 0
        }
        else{
            return budgetItems!!.size
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomBudgetViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.budget_item_layout, parent, false)
        return CustomBudgetViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomBudgetViewHolder, position: Int) {
        val date = budgetItems!![position].time

        holder.itemView.budgetItemTitle?.text = budgetItems!![position].title
        holder.itemView.budgetItemAmount.text = budgetItems!![position].amount.toString()
        holder.itemView.budgetItemTime.text = dateFormat.format(date)

        val id = budgetItems!![position].id
        val category = budgetItems!![position].category

        when (category) {
            "Income" -> holder.itemView.budgetItemImage.setImageResource(R.drawable.income)
            "Expense" -> holder.itemView.budgetItemImage.setImageResource(R.drawable.expense)

            "Salary" -> holder.itemView.budgetItemImage.setImageResource(R.drawable.salary)
            "Extra Income" -> holder.itemView.budgetItemImage.setImageResource(R.drawable.extra_income)
            "Gift" -> holder.itemView.budgetItemImage.setImageResource(R.drawable.gift)
            "Refund" -> holder.itemView.budgetItemImage.setImageResource(R.drawable.refund)
            "Rent" -> holder.itemView.budgetItemImage.setImageResource(R.drawable.rent)
            "Food" -> holder.itemView.budgetItemImage.setImageResource(R.drawable.food)
            "Entertainment" -> holder.itemView.budgetItemImage.setImageResource(R.drawable.entertainment)
            "Transportation" -> holder.itemView.budgetItemImage.setImageResource(R.drawable.transportation)
            "Other Expense" -> holder.itemView.budgetItemImage.setImageResource(R.drawable.expense)

        }

        holder.parentLayout.setOnClickListener() {user->
            val intent = Intent(context, AddNewItemActivity::class.java);
            intent.putExtra("ID", id);
            context!!.startActivity(intent);
         }

    }
}

class CustomBudgetViewHolder(v: View): RecyclerView.ViewHolder(v){
    var parentLayout = v.budgetItem;

}