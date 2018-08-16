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
import com.github.mikephil.charting.formatter.PercentFormatter
import kotlinx.android.synthetic.main.activity_summary_view.*


class SummaryViewActivity : AppCompatActivity() {

    var colors :MutableList<Int> = arrayListOf(
            Color.parseColor("#509993"),Color.parseColor("#153754"),Color.parseColor("#B0A596")
            ,Color.parseColor("#911E07"),Color.parseColor("#D4C8A7"),Color.parseColor("#E39C13"))

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

        customizePie(expensePie,pieExpenseArrayList)
        customizePie(incomePie,pieIncomeArrayList)

    }

    private fun customizePie(pie: PieChart?, pieDataArray: ArrayList<PieEntry>) {

        var pieDataSet = PieDataSet(pieDataArray, "")

        pieDataSet!!.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        pieDataSet.valueFormatter = PercentFormatter()
        pieDataSet.valueTextSize = 15f
        pieDataSet.valueTextColor = Color.WHITE
        pieDataSet.colors = colors

        pie!!.data = PieData(pieDataSet)
        pie.setUsePercentValues(true)
        pie.description.isEnabled = false
        pie.setHoleColor(Color.parseColor("#444444"))
        pie.animateY(1500)
        pie.setEntryLabelTextSize(20f)
        pie.setEntryLabelColor(Color.WHITE)
        pie.legend.textSize = 15f
        pie.legend.textColor = Color.WHITE
        pie.setBackgroundColor(Color.DKGRAY)
        pie.legend.isWordWrapEnabled = true

        pie.invalidate()
    }


}
