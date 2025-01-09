package com.example.chensproject

import android.provider.ContactsContract.CommonDataKinds.Phone

class Customer {
    private var Name: String
    private var Phone: String
    private var Queuelist = listOf<queue>()

    constructor(Name: String, Phone: String, Queuelist: List):
            this(Name,Phone,Queuelist)

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
    fun setQueuelist(newQueuelist: list<queue>)
        Queuelist=newQueuelist
}