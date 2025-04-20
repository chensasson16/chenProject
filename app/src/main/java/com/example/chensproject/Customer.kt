package com.example.chensproject

class Customer(
    var name: String = "",
    var phone: String = "",
    private var queuelist: MutableList<Queue> = mutableListOf()
) {

    fun getQueuelist(): List<Queue> {
        return queuelist
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
