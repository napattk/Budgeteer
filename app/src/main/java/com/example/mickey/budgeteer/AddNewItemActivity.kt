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
import java.sql.Timestamp
import java.text.SimpleDateFormat


class AddNewItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_item)
        var dateFormat = SimpleDateFormat("dd MMM yyyy")
        val categories = arrayOf("Income", "Expense")

        var calendar = Calendar.getInstance()

        categorySpinner.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,categories)


        var id = intent.getIntExtra("ID",-1)

        //Check if editing or adding new
        if(id > -1){
            setTitle("Editing item")
            deleteButton.visibility = View.VISIBLE;

            var dbHandler = DBHandler(this)
            var budget = dbHandler.readDataFromID(id)
            titleEdit.setText(budget.title)
            categorySpinner.setSelection(categories.indexOf(budget.type))
            amountEdit.setText(budget.amount.toString())

            var stamp = Timestamp(budget.time!!)
            calendar.time = Date(stamp.time)

        }

        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)

        val months = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun","Jul","Aug","Sep","Oct","Nov","Dec")

        dateEdit.setText(dateFormat.format(calendar.time))

        dateEdit.setOnClickListener{
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, dpYear, dpMonth, dpDay ->
                var stringMonth = months.get(dpMonth)
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
            else if(categorySpinner.selectedItem==null){
                Toast.makeText(this, "Please select a category.", Toast.LENGTH_SHORT).show()
            }
            else{
               var dbHandler = DBHandler(this)

                val formatter = SimpleDateFormat("yyyy/MM/dd")
                val dateString = "" + year + "/"+ (month+1)+ "/" + day
                val date = formatter.parse(dateString)
                val msDate = date.time;

                if (title.equals("Editing item")){
                    dbHandler.updateData(titleEdit.text.toString(), categorySpinner.selectedItem.toString(), amountEdit.text.toString().toInt(), id, msDate)
                }else {
                    dbHandler.insertData(titleEdit.text.toString(), categorySpinner.selectedItem.toString(), amountEdit.text.toString().toInt(), msDate)
                }
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

        deleteButton.setOnClickListener{user->
            var dbHandler = DBHandler(this)
            dbHandler.deleteData(id);

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }



    }
}
