package com.example.chensproject

class queue (customer: Customer, hour: String, date: String, atBussines: Buissnes){
    private lateinit var Customer: Customer
    private lateinit var hour: String
    private lateinit var date: String
    private var atBussines: Buissnes

    init {
        this.Customer  = customer
        this.hour= hour
        this.date = date
        this.atBussines = atBussines
    }

    fun getCustomer(): Customer {
        return Customer
    }
    fun gethour(): String {
        return hour
    }
    fun getdate(): String {
        return date
    }
    fun getatBussines(): Buissnes {
        return atBussines
    }


    fun setCustomer(newCustomer:Customer){
        Customer=newCustomer
    }
    fun sethour(newhour: String){
        hour=newhour
    }
    fun setdate(newdate: String){
        date=newdate
    }
    fun setatBussines(newatBussines: Buissnes){
        atBussines=newatBussines
    }


}