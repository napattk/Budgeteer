package com.example.mickey.budgeteer

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import java.util.*
import android.widget.Spinner
import android.widget.ArrayAdapter
import com.example.mickey.budgeteer.R.drawable.calendar
import kotlinx.android.synthetic.main.activity_month_picker.*


class monthPickerActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_month_picker)
        this.setTitle("Select Month")

        var months = arrayOf("January", "February","March","April","May","June","July","August","September","October","November","December")
        var years = arrayListOf<String>()

        val thisYear = Calendar.getInstance().get(Calendar.YEAR)
        for (i in thisYear downTo 1900) {
            years.add(Integer.toString(i))
        }

        val yearAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, years)
        val monthAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,months)

        monthSpinner.adapter = monthAdapter
        yearSpinner.adapter = yearAdapter

        monthSpinner.setSelection(Calendar.getInstance().get(Calendar.MONTH))

        monthSubmitButton.setOnClickListener { user->
            var intent = Intent(this, MainActivity::class.java)
            intent.putExtra("month", monthSpinner.selectedItem.toString())
            intent.putExtra("year", yearSpinner.selectedItem.toString())
            startActivity(intent)
        }

        showAllButton.setOnClickListener { user ->
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }




    }
}
