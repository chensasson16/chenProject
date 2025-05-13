package com.example.chensproject

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QueueCardAdpater(
    private var queueList: MutableList<Queue>,
    private val context: Context
) : RecyclerView.Adapter<QueueCardAdpater.queueViewHolder>() {

    class queueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Make sure these IDs exactly match what's in your bussinescard.xml layout
        val customerName: TextView = itemView.findViewById(R.id.customerName)
        val date: TextView = itemView.findViewById(R.id.date)
        val cancel: TextView = itemView.findViewById(R.id.cancel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): queueViewHolder {
        // Use the correct layout file
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.queue_card, parent, false)
        return queueViewHolder(view)
    }

    override fun getItemCount(): Int {
        return queueList.size
    }

    override fun onBindViewHolder(holder: queueViewHolder, position: Int) {
        val queue = queueList[position]
        holder.customerName.text = queue.getCustomer().name
        holder.date.text = "${queue.getdate()} שעה : ${queue.gethour()}"
        if (queue.getCustomer().name == null){
            holder.cancel.text = "קבע תור"
        }
        //ביטול תור קיים
        holder.cancel.setOnClickListener {
        }
    }


    // Update function
    fun updateQueueList(newQueueList: List<Queue>) {
        queueList.clear()
        queueList.addAll(newQueueList)
        notifyDataSetChanged()
    }
}