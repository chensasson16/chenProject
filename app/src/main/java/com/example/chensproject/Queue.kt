package com.example.chensproject


class Queue(customer: Customer?, hour: String, date: String, atBussines: Buissnes?){
    private  var Customer: Customer = Customer()
    private lateinit var hour: String
    private lateinit var date: String
    private  var atBussines: Buissnes = Buissnes()
    private var isAviable : Boolean = true

    init {
        if (customer != null) {
            this.Customer  = customer
        }
        this.hour= hour
        this.date = date
        if (atBussines != null) {
            this.atBussines = atBussines
        }
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

    //פעולות של המחלקה
    fun setCustomer(newCustomer:Customer){
        Customer=newCustomer
        isAviable = false
    }
    fun sethour(newhour: String){
        hour=newhour
    }
    fun setdate(newdate: String){
        date=newdate
    }
    fun setAtBussines(newAtBussines: Buissnes){
        atBussines=newAtBussines
    }
    fun isTaken(newqueue: Queue): Boolean{
        return !isAviable
    }


}