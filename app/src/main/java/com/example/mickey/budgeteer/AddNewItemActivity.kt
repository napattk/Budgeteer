package com.example.mickey.budgeteer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_new_item.*

class AddNewItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_item)

        val categories = arrayOf("Income", "Expense")

        categorySpinner.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,categories)


        var position = intent.getIntExtra("Position",-1)
        var id = position +1;
        println(position);
        if(position > -1){
            setTitle("Editing item")
            deleteButton.visibility = View.VISIBLE;

            var dbHandler = DBHandler(this)
            var budget = dbHandler.readData(id)
            titleEdit.setText(budget.title)
            categorySpinner.setSelection(categories.indexOf(budget.type))
            amountEdit.setText(budget.amount.toString())
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
                if (title.equals("Editing item")){
                    dbHandler.updateData(titleEdit.text.toString(), categorySpinner.selectedItem.toString(), amountEdit.text.toString().toInt(), id)
                }else {
                    dbHandler.insertData(titleEdit.text.toString(), categorySpinner.selectedItem.toString(), amountEdit.text.toString().toInt())
                }
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        deleteButton.setOnClickListener{user->
            var dbHandler = DBHandler(this)
            dbHandler.deleteData(id);

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }



    }
}
