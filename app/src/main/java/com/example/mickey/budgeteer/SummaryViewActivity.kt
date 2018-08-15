package com.example.mickey.budgeteer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class SummaryViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary_view)

        val year = intent.getStringExtra("year")
        val month =  intent.getStringExtra("month")

        val dbHandler = DBHandler(this)
        var budgetItems: MutableList<Budget>? = null;

        if(year !=null) {
            budgetItems = dbHandler.readDataFromMonth(year, month)
        }
        else{
            budgetItems = dbHandler.readAllData()
        }

        var total = 0
        val categoryTotalHash = HashMap<String,Int>()

        budgetItems.iterator().forEach {
            total += it.amount!!

            if(categoryTotalHash.get(it.category!!)!=null) {
                val originalHash = categoryTotalHash.getValue(it.category!!)
                categoryTotalHash.put(it.category!!,originalHash + it.amount!!)
            } else {
                categoryTotalHash.put(it.category!!,it.amount!!)
            }

        }

        for ((key, value) in categoryTotalHash) {
            println("$key = $value")
        }

    }
}
