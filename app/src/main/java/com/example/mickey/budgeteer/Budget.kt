package com.example.mickey.budgeteer

class Budget{
    var id:Int? = null;
    var title: String? = null;
    var amount: Int? = null;
    var type: String? = null;
    var time: Long? = null;

    constructor(title: String?, amount:Int?, type:String?, time:Long?){
        this.title = title;
        this.amount = amount;
        this.type = type;
        this.time = time;
    }

    constructor(){

    }
}