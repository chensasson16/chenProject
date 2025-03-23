package com.example.chensproject

class Customer(name: String, phone: String, queuelist: List<Queue>) {
    var name: String = name
        private set

    var phone: String = phone
        private set

    private var queuelist = queuelist.toMutableList()

    fun getQueuelist(): List<Queue> {
        return queuelist
    }

    fun setName(newName: String) {
        name = newName
    }

    fun setPhone(newPhone: String) {
        phone = newPhone
    }

    fun setQueuelist(newQueuelist: List<Queue>) {
        queuelist = newQueuelist.toMutableList()
    }

    fun cancelQueue(queue: Queue) {
        queuelist.remove(queue)
    }

    fun addQueue(queue: Queue) {
        queuelist.add(queue)
    }
}
