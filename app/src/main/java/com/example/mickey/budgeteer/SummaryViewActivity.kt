package com.example.mickey.budgeteer

import android.graphics.Color
import android.graphics.Paint
import android.opengl.Visibility
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.activity_summary_view.*


class SummaryViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary_view)

        title = "Summary"

        //Get info from DB
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

        //Separate Data
        var total = 0
        val categoryTotalHash = HashMap<String,Int>()
        val pieExpenseArrayList = ArrayList<PieEntry>();
        val pieIncomeArrayList = ArrayList<PieEntry>();

        var colors :MutableList<Int> = arrayListOf(
                Color.parseColor("#509993"),Color.parseColor("#153754"),Color.parseColor("#B0A596")
                ,Color.parseColor("#911E07"),Color.parseColor("#D4C8A7"),Color.parseColor("#E39C13"))

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
            when("$key"){
                in CategoryViewAdapter.incomeItems -> pieIncomeArrayList.add(PieEntry("$value".toFloat(),"$key"))
                in CategoryViewAdapter.expenseItems -> pieExpenseArrayList.add(PieEntry("$value".toFloat(),"$key"))
            }
        }

        if(pieIncomeArrayList.size == 0){
            noIncomeText.visibility = View.VISIBLE
        }
        if(pieExpenseArrayList.size == 0){
            noExpenseText.visibility = View.VISIBLE
        }

        var pieExpenseDataSet = PieDataSet(pieExpenseArrayList, "")
        var pieIncomeDataSet = PieDataSet(pieIncomeArrayList, "")

        pieExpenseDataSet.colors = colors
        pieIncomeDataSet.colors = colors

        expensePie.data = PieData(pieExpenseDataSet)
        incomePie.data = PieData(pieIncomeDataSet)

        expensePie.description.isEnabled = false
        incomePie.description.isEnabled = false

        expensePie.animateY(1500)
        incomePie.animateY(1500)

        expensePie.invalidate()
        incomePie.invalidate()

        expensePie.setEntryLabelTextSize(20f)
        incomePie.setEntryLabelTextSize(20f)

        expensePie.setEntryLabelColor(Color.WHITE)
        incomePie.setEntryLabelColor(Color.WHITE)

        expensePie.legend.textSize = 15f
        incomePie.legend.textSize = 15f

        expensePie.legend.textColor = Color.WHITE
        incomePie.legend.textColor = Color.WHITE

        expensePie.setBackgroundColor(Color.DKGRAY)
        incomePie.setBackgroundColor(Color.DKGRAY)

        expensePie.setHoleColor(Color.DKGRAY)
        incomePie.setHoleColor(Color.DKGRAY)


    }




}
