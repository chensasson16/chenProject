package com.example.chensproject

import android.widget.Button
import android.widget.EditText
import java.util.jar.Attributes.Name


class Buissnes
{
    private var Name: String
    private var aboutMe: String
    private val customersList = listOf<Customer>()
    private var prices: String;

    constructor(Name: String, aboutMe: String, customersList: List , prices: String):
            this(Name, aboutMe, customersList,prices)
 // פעולות מאחזרות

    fun getName(): String {
        return Name
    }

    fun getaboutMe(): String{
        return aboutMe
    }

    fun getcustomersList(): List<Customer>{
        return customersList
    }

    fun getprices():String{
        return prices
    }
// פעולות קובעות

    fun setName(newName: String){
        Name=newName
    }
    fun setaboutMe(newaboutMe: String){
        aboutMe=newaboutMe
    }
    fun setcustomersList(newcustomersList: List<Customer>){
        customersList=newcustomersList
    }
    fun setprices(newprices: String){
        prices=newprices
    }


}
fun main() {
    val Buissnes = Buissnes()
}