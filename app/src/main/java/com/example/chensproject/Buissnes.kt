package com.example.chensproject

import android.widget.Button
import android.widget.EditText
import java.util.Queue
import java.util.jar.Attributes.Name


class Buissnes(Name: String, aboutMe: String, queeuList: List<queue> ,customersList: List<Customer> , prices: String)
{
    private lateinit var Name: String
    private lateinit var aboutMe: String
    private var customersList = mutableListOf<Customer>()
    private lateinit var prices: String;
    private var possiblequeues= mutableListOf<queue>()
    private var queueList= mutableListOf<queue>()
    init{
        this.Name=Name
        this.aboutMe=aboutMe
        this.customersList=customersList.toMutableList()
        this.prices=prices
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
        customersList=newcustomersList.toMutableList()
    }
    fun setprices(newprices: String){
        prices=newprices
    }
    fun setpossiblequeues(newpossiblequeues: List<queue>){
        possiblequeues=newpossiblequeues.toMutableList()
    }
    fun changeName(newName: String){
        Name=newName
    }
    fun changePrices(newprices: String){
        prices=newprices
    }
    fun cancleQueue(newQueue: queue){
        queueList.remove(newQueue)
        newQueue.getCustomer().cancleQueue(newQueue)
    }
    fun addPossiblequeue(newQueue: queue){
        possiblequeues.add(newQueue)
    }
}
