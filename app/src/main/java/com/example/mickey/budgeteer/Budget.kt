package com.example.mickey.budgeteer

class Budget{
    var title: String? = null;
    var subText: String? = null;
    var image: String? = null;

    constructor(title: String?, subText:String?, image:String?){
        this.title = title;
        this.subText = subText;
        this.image = image;
    }
}