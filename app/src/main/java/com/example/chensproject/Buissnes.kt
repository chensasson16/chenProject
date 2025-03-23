package com.example.chensproject

import android.location.Location
import android.widget.Button
import android.widget.EditText
import java.util.Queue
import java.util.jar.Attributes.Name


class Buissnes(Name: String, aboutMe: String, queeuList: List<com.example.chensproject.Queue> ,customersList: List<Customer> , prices: String)
{
    private lateinit var Name: String
    private lateinit var aboutMe: String
    private var customersList = mutableListOf<Customer>()
    private lateinit var prices: String;
    private lateinit var Location: String
    private var possiblequeues= mutableListOf<com.example.chensproject.Queue>()
    private var queueList= mutableListOf<com.example.chensproject.Queue>()
    init{
        this.Name=Name
        this.aboutMe=aboutMe
        this.customersList=customersList.toMutableList()
        this.prices=prices
        this.Location=Location
        this.possiblequeues=possiblequeues
        this.queueList = queueList.toMutableList()
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

    fun getlocation(): String{
        return Location
    }

    fun getpossiblequeues(): List<com.example.chensproject.Queue>{
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
        customersList=newcustomersList.toMutableList()
    }
    fun setprices(newprices: String){
        prices=newprices
    }
    fun setlocation(newLocation: Location){
        Location = newLocation.toString()
    }



    fun setpossiblequeues(newpossiblequeues: List<com.example.chensproject.Queue>){
        possiblequeues=newpossiblequeues.toMutableList()
    }
    // פעולות של המחלקה
    // שינוי שם עסק
    fun changeName(newName: String){
        Name=newName
    }
    //שינוי מחיר
    fun changePrices(newprices: String){
        prices=newprices
    }
    //ביטול תור
    fun cancleQueue(newQueue:com.example.chensproject.Queue){
        queueList.remove(newQueue)
        newQueue.getCustomer().cancelQueue(newQueue)
    }
    //הוספת תורים אפשריים
    fun addPossiblequeue(newQueue: com.example.chensproject.Queue){
        possiblequeues.add(newQueue)
    }
    //העברת תור תפוס לרשימת התורים והורדה מהרשימה של התורים הפנויים
    fun updatedQueue(newQueue: com.example.chensproject.Queue){
        possiblequeues.add(newQueue)
        queueList.remove(newQueue)
    }
    // שינוי מיקום
    fun changeLocation(newLocation: Location){
        Location= newLocation.toString() }

}