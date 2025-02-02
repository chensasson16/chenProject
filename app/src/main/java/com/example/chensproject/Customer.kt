package com.example.chensproject

import android.provider.ContactsContract.CommonDataKinds.Phone
import com.google.common.collect.Queues

class Customer (Name: String, Phone: String, Queuelist: List<queue>){
    private var Name: String
    private var Phone: String
    private var Queuelist = listOf<queue>()

    init {
        this.Name=Name
        this.Phone=Phone
        this.Queuelist= Queuelist
    }


    fun getName(): String {
        return Name
    }

    fun getPhone(): String {
        return Phone
    }

    fun getQueuelist(): List<queue> {
        return Queuelist
    }

    fun setName(newName: String){
        Name=newName
    }
    fun setPhone(newPhone: String){
        Phone=newPhone
    }
    fun setQueuelist(newQueuelist: List<queue>){
        Queuelist = newQueuelist
    }

}