package com.example.chensproject

import android.provider.ContactsContract.CommonDataKinds.Phone
import com.google.common.collect.Queues

class Customer (Name: String, Phone: String, Queuelist: List<Queue>){
    private var Name: String
    private var Phone: String
    private var Queuelist = mutableListOf<Queue>()
//פעולה בונה
    init {
        this.Name=Name
        this.Phone=Phone
        this.Queuelist= Queuelist.toMutableList()
    }


    fun getName(): String {
        return Name
    }

    fun getPhone(): String {
        return Phone
    }

    fun getQueuelist(): List<Queue> {
        return Queuelist
    }

    fun setName(newName: String){
        Name=newName
    }
    fun setPhone(newPhone: String){
        Phone=newPhone
    }
    fun setQueuelist(newQueuelist: List<Queue>){
        Queuelist = newQueuelist.toMutableList()
    }
    fun cancleQueue(newqueue:Queue ){
        Queuelist.remove(newqueue)
    }
    fun addQueue(newqueue: Queue){
        Queuelist.add(newqueue)
    }
}