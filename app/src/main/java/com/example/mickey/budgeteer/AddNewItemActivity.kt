package com.example.mickey.budgeteer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_new_item.*
import java.util.*
import android.app.DatePickerDialog
import kotlinx.android.synthetic.main.category_item_layout.*
import java.sql.Timestamp
import java.text.SimpleDateFormat


class AddNewItemActivity : AppCompatActivity() {

    override fun onSaveInstanceState(outState: Bundle?) {
        outState!!.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_item)

        val dateFormat = SimpleDateFormat("dd MMM yyyy")
        val categories = arrayOf("Income", "Expense")
        val calendar = Calendar.getInstance()
        //categorySpinner.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,categories)

        val id = intent.getIntExtra("ID",-1)

        //Check if editing or adding new
        if(id > -1){
            setTitle("Editing item")
            deleteButton.visibility = View.VISIBLE;

            val dbHandler = DBHandler(this)
            val budget = dbHandler.readDataFromID(id)
            titleEdit.setText(budget.title)
            //categorySpinner.setSelection(categories.indexOf(budget.type))
            amountEdit.setText(budget.amount.toString())

            val stamp = Timestamp(budget.time!!)
            calendar.time = Date(stamp.time)

        }

        categoryEdit.setOnClickListener {
            val ft = supportFragmentManager.beginTransaction()
            val prev = supportFragmentManager.findFragmentByTag("dialog")
            if (prev != null) {
                ft.remove(prev)
            }
            ft.addToBackStack(null)

            val dialogFragment = CategoryPickerDialog(categoryEdit)
            dialogFragment.show(ft, "dialog")

        }

        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)

        val months = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun","Jul","Aug","Sep","Oct","Nov","Dec")

        dateEdit.setText(dateFormat.format(calendar.time))

        dateEdit.setOnClickListener{
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, dpYear, dpMonth, dpDay ->
                val stringMonth = months.get(dpMonth)
                dateEdit.setText("" + dpDay + "/" +  stringMonth + "/" + dpYear)
                year = dpYear
                month = dpMonth
                day = dpDay
            }, year, month, day)
            dpd.show()
        }

        submitButton.setOnClickListener{user->
            if(titleEdit.text.isBlank()){
                Toast.makeText(this, "Please enter a title.", Toast.LENGTH_SHORT).show()
            }
            else if(amountEdit.text.isBlank()){
                Toast.makeText(this, "Please enter an amount.", Toast.LENGTH_SHORT).show()
            }
            else if(categoryEdit.text.isBlank()){
                Toast.makeText(this, "Please select a category.", Toast.LENGTH_SHORT).show()
            }
            else{
                val dbHandler = DBHandler(this)

                val formatter = SimpleDateFormat("yyyy/MM/dd")
                val dateString = "" + year + "/"+ (month+1)+ "/" + day
                val date = formatter.parse(dateString)
                val msDate = date.time;

                if (title.equals("Editing item")){
                    dbHandler.updateData(titleEdit.text.toString(), categoryEdit.toString(), amountEdit.text.toString().toInt(), id, msDate)
                }else {
                    dbHandler.insertData(titleEdit.text.toString(), categoryEdit.toString(), amountEdit.text.toString().toInt(), msDate)
                }
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

        deleteButton.setOnClickListener{user->
            val dbHandler = DBHandler(this)
            dbHandler.deleteData(id);

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        tempButton.setOnClickListener{user->

            val ft = supportFragmentManager.beginTransaction()
            val prev = supportFragmentManager.findFragmentByTag("dialog")
            if (prev != null) {
                ft.remove(prev)
            }
            ft.addToBackStack(null)

            val dialogFragment = CategoryPickerDialog(tempButton)
            dialogFragment.show(ft, "dialog")

        }

    }

}
