package com.example.chensproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView

class HoursAdapter(
    private val hours: MutableList<String>,
    private val onHourClicked: (String, Int) -> Unit
) : RecyclerView.Adapter<HoursAdapter.HourViewHolder>() {

    class HourViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val hourText: TextView = itemView.findViewById(R.id.date)
        val bookButton: Button = itemView.findViewById(R.id.addQueueButton)
        val cancelButton: TextView = itemView.findViewById(R.id.cancel)
        val name: TextView = itemView.findViewById(R.id.customerName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.queue_card, parent, false)
        return HourViewHolder(view)
    }

    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
        val hour = hours[position]
        holder.cancelButton.isVisible = false
        holder.name.isVisible = false
        holder.hourText.text = hour

        holder.bookButton.setOnClickListener {
            onHourClicked(hour, position)
        }
    }

    fun removeHourAt(position: Int) {
        hours.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, hours.size)
    }

    override fun getItemCount() = hours.size
}
