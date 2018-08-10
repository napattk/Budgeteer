package com.example.mickey.budgeteer

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val DB_NAME = "Budget";
val TABLE_NAME = "Expenses";
val COL_TITLE = "title";
val COL_SUBTEXT = "subtext";
val COL_IMAGE = "image";
val COL_ID = "id";


class DBHandler(var context: Context) : SQLiteOpenHelper(context,DB_NAME,null,1){

    override fun onCreate(db: SQLiteDatabase?) {//When creating DB
        val createTable = "CREATE TABLE " + TABLE_NAME + "("+
                COL_ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COL_TITLE + " VARCHAR(256), " +
                COL_SUBTEXT + " VARCHAR(256), " +
                COL_IMAGE + " VARCHAR(256) "

        db?.execSQL(createTable)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {//If DB already created



    }

    fun insertData(title: String?, subText:String?, image:String?){
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_TITLE, title)
        cv.put(COL_SUBTEXT, subText)
        cv.put(COL_IMAGE, image)

        var result = db.insert(TABLE_NAME,null,cv);
        if(result == -1.toLong()){
            println("Database Error");
            Toast.makeText(context, "Item couldn't be added.",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Item added successfully.",Toast.LENGTH_SHORT).show();
        }
    }




}