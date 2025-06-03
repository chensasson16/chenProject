package com.example.chensproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class QueueListAdapter(
    private val queueList: MutableList<Queue>,
    private val businessEmail: String
) : RecyclerView.Adapter<QueueListAdapter.QueueViewHolder>() {

    class QueueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val customerName: TextView = itemView.findViewById(R.id.customerName)
        val date: TextView = itemView.findViewById(R.id.date)
        val cancelButton: TextView = itemView.findViewById(R.id.cancel)
        val add: Button = itemView.findViewById(R.id.addQueueButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueueViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.queue_card, parent, false)
        return QueueViewHolder(view)
    }

    override fun onBindViewHolder(holder: QueueViewHolder, position: Int) {
        val queue = queueList[position]
        val context = holder.itemView.context

        holder.customerName.text = queue.customerEmail
        holder.date.text = "${queue.date} - ${queue.hour}"
        holder.add.visibility = View.GONE

        holder.cancelButton.setOnClickListener {
            val db = FirebaseFirestore.getInstance()
            val businessRef = db.collection("buissness").document(businessEmail)

            businessRef.update("queueTaken", com.google.firebase.firestore.FieldValue.arrayRemove(queue))
                .addOnSuccessListener {
                    // הסרה מרשימת ה-Adapter
                    queueList.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, queueList.size)

                    Toast.makeText(context, "✅ התור בוטל בהצלחה", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "❌ שגיאה בביטול", Toast.LENGTH_SHORT).show()
                }
        }
    }



    override fun getItemCount(): Int = queueList.size
}
