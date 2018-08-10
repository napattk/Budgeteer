package com.example.mickey.budgeteer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        budgetList.layoutManager = LinearLayoutManager(this);
        budgetList.adapter = BudgetViewAdapter();

        newItemButton.setOnClickListener(){ user->
            val intent = Intent(this, AddNewItemActivity::class.java);
            startActivity(intent);
        }

    }


}
