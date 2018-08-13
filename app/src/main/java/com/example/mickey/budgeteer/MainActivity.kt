package com.example.mickey.budgeteer

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.setTitle("Transactions")

        //Prepare DB
        var dbHandler = DBHandler(this)
        var budgetItems :MutableList<Budget>

        //Check intent
        var year = intent!!.getStringExtra("year")
        var month = intent!!.getStringExtra("month")

        if(year != null && month != null){
            budgetItems = dbHandler.readDataFromMonth(year,month)
            this.setTitle("Transactions from " + month + " "+ year)
        }else{
            budgetItems = dbHandler.readAllData()
            this.setTitle("All Transactions")
        }

        //Calculate total change
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
        budgetList.adapter = BudgetViewAdapter(this, budgetItems);

        newItemButton.setOnClickListener(){ user->
            val intent = Intent(this, AddNewItemActivity::class.java);
            startActivity(intent);
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.month_select_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.monthSelect -> {
                val intent = Intent(this, monthPickerActivity::class.java)
                startActivity(intent)
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }


}
