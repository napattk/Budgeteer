package com.example.mickey.budgeteer

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

val DB_NAME = "Budget";
val TABLE_NAME = "Expenses";
val COL_TITLE = "title";
val COL_CATEGORY = "category";
val COL_AMOUNT = "amount";
val COL_TIME = "time";
val COL_ID = "id";


class DBHandler(var context: Context) : SQLiteOpenHelper(context,DB_NAME,null,1){

    override fun onCreate(db: SQLiteDatabase?) {//When creating DB
        val createTable = "CREATE TABLE " + TABLE_NAME + "("+
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COL_TITLE + " VARCHAR(256), " +
                COL_CATEGORY + " VARCHAR(256), " +
                COL_TIME + " LONG, " +
                COL_AMOUNT + " INTEGER)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {//If DB already created

    }

    fun insertData(title: String?, type:String?, amount:Int?, time:Long?){
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_TITLE, title)
        cv.put(COL_CATEGORY, type)
        cv.put(COL_AMOUNT, amount)
        cv.put(COL_TIME, time)

        val result = db.insert(TABLE_NAME,null,cv);
        if(result == -1.toLong()){
            println("Database Error");
            Toast.makeText(context, "Item couldn't be added.",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Item added successfully.",Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    fun readAllData() : MutableList<Budget>{
        val list : MutableList<Budget> = ArrayList()

        val db = this.readableDatabase
        val query = "Select * from " + TABLE_NAME + " ORDER BY " + COL_TIME + " DESC "
        val result = db.rawQuery(query, null)

        if(result.moveToFirst()){
            do{
                val budget = Budget()
                budget.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                budget.title = result.getString(result.getColumnIndex(COL_TITLE))
                budget.amount = result.getString(result.getColumnIndex(COL_AMOUNT)).toInt()
                budget.category = result.getString(result.getColumnIndex(COL_CATEGORY))
                budget.time = result.getString(result.getColumnIndex(COL_TIME)).toLong()
                list.add(budget)
            }while(result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }

    fun readDataFromID(id :Int) : Budget{
        val db = this.readableDatabase
        val query = "Select *  FROM "+ TABLE_NAME + " WHERE id = " + id
        val result = db.rawQuery(query, null)
        result.moveToFirst()

        val budget = Budget()
        budget.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
        budget.title = result.getString(result.getColumnIndex(COL_TITLE))
        budget.amount = result.getString(result.getColumnIndex(COL_AMOUNT)).toInt()
        budget.category = result.getString(result.getColumnIndex(COL_CATEGORY))
        budget.time = result.getString(result.getColumnIndex(COL_TIME)).toLong()

        return budget;
    }

    fun updateData(title: String?, type:String?, amount:Int?, id: Int?, time:Long?){
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_TITLE, title)
        cv.put(COL_CATEGORY, type)
        cv.put(COL_AMOUNT, amount)
        cv.put(COL_TIME, time)

        db.update(TABLE_NAME,cv, COL_ID+"="+ id, null);

        db.close();
    }

    fun deleteData(id: Int?){
        val db = this.writableDatabase
        db.delete(TABLE_NAME, COL_ID+"=" + id, null) > 0;
    }

    fun readDataFromMonth(year: String?, month: String?) : MutableList<Budget>{
        val list : MutableList<Budget> = ArrayList()

        //Calculate range
        val calendar = Calendar.getInstance()
        val months = arrayOf("January", "February","March","April","May","June","July","August","September","October","November","December")

        val formatter = SimpleDateFormat("yyyy MM dd")
        calendar.set(year!!.toInt(),months.indexOf(month),1)
        val maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val startDateString = "" + year + " "+ (months.indexOf(month)+1)+ " " + 1
        val endDateString = "" + year + " "+ (months.indexOf(month)+1)+ " " + maxDays
        val startDate = formatter.parse(startDateString)
        val endDate = formatter.parse(endDateString)
        val msDateStart = startDate.time;
        val msDateEnd = endDate.time;

        val db = this.readableDatabase
        val query = "Select * from " + TABLE_NAME +
                " WHERE "+ COL_TIME + " BETWEEN " + msDateStart + " AND " + msDateEnd +
                " ORDER BY " + COL_TIME + " DESC "
        val result = db.rawQuery(query, null)

        if(result.moveToFirst()){
            do{
                val budget = Budget()
                budget.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                budget.title = result.getString(result.getColumnIndex(COL_TITLE))
                budget.amount = result.getString(result.getColumnIndex(COL_AMOUNT)).toInt()
                budget.category = result.getString(result.getColumnIndex(COL_CATEGORY))
                budget.time = result.getString(result.getColumnIndex(COL_TIME)).toLong()
                list.add(budget)
            }while(result.moveToNext())
        }

        result.close()
        db.close()
        return list

    }

}