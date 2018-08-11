package com.example.mickey.budgeteer

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Calculate total change
        var dbHandler = DBHandler(this)
        var budgetItems = dbHandler.readAllData()
        var totalChange = 0;

        budgetItems.iterator().forEach {
            when(it.type){
                "Expense" -> totalChange = totalChange - it.amount!!;
                "Income" -> totalChange = totalChange + it.amount!!;
            }
        }

        if(totalChange < 0){
            changeAmountText.setTextColor(Color.parseColor("#8C2A2A"))
            changeAmountText.setText(""+totalChange)
            changeImage.setImageResource(R.drawable.decrease)
        }else{
            changeAmountText.setTextColor(Color.parseColor("#587C12"))
            changeAmountText.setText("+"+totalChange)
            changeImage.setImageResource(R.drawable.increase)
        }

        //Set-up items list
        budgetList.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?;
        budgetList.adapter = BudgetViewAdapter(this);

        newItemButton.setOnClickListener(){ user->
            val intent = Intent(this, AddNewItemActivity::class.java);
            startActivity(intent);
        }

    }


}
