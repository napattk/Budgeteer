package com.example.mickey.budgeteer

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.setTitle("Transactions")

        //Prepare DB
        val dbHandler = DBHandler(this)
        val budgetItems :MutableList<Budget>

        //Check intent
        val year = intent!!.getStringExtra("year")
        val month = intent!!.getStringExtra("month")

        if(year != null && month != null){
            budgetItems = dbHandler.readDataFromMonth(year,month)
            this.setTitle(month + " " + year + " Transactions")
        }else{
            budgetItems = dbHandler.readAllData()
            this.setTitle("All Transactions")
        }

        //Calculate total change
        var totalChange = 0;

        budgetItems.iterator().forEach {
            when(it.category){
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

        //Set-up budget items list
        budgetListRecyclerView.layoutManager = LinearLayoutManager(this)
        budgetListRecyclerView.adapter = BudgetViewAdapter(this, budgetItems)

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
                val intent = Intent(this, MonthPickerActivity::class.java)
                startActivity(intent)
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }


}
