package com.example.chensproject

import android.widget.Button
import android.widget.EditText
import java.util.jar.Attributes.Name


class Buissnes(Name: String, aboutMe: String, customersList: List , prices: String)
{
    private lateinit var Name: String
    private lateinit var aboutMe: String
    private var customersList = listOf<Customer>()
    private lateinit var prices: String;
    private var possiblequeues= listOf<queue>()
    init{
        this.Name=Name
        this.aboutMe=aboutMe
        this.customersList=customersList
        this.prices=prices
        this.possiblequeues=possiblequeues
    }

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

    fun getpossiblequeues(): List<queue>{
        return possiblequeues
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
    fun setpossiblequeues(newpossiblequeues: List<queue>){
        possiblequeues=newpossiblequeues
    }



}
fun main() {
    val Buissnes = Buissnes()
}