package com.example.mickey.budgeteer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_new_item.*

class AddNewItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_item)

        val categories = arrayOf("Income", "Expense")

        titleEdit.text
        categorySpinner.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,categories)

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
                dbHandler.insertData(titleEdit.text.toString(),categorySpinner.selectedItem.toString(),amountEdit.text.toString().toInt())
            }

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }


    }
}
