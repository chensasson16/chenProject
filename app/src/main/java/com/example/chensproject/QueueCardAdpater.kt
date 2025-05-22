package com.example.chensproject

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class QueueCardAdpater(
    private var queueList: MutableList<Queue>,
    private val context: Context,
    private val businessEmail: String

) : RecyclerView.Adapter<QueueCardAdpater.queueViewHolder>() {

    class queueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val customerName: TextView = itemView.findViewById(R.id.customerName)
        val date: TextView = itemView.findViewById(R.id.date)
        val cancel: TextView = itemView.findViewById(R.id.cancel)
        val addQueueButton: Button = itemView.findViewById(R.id.addQueueButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): queueViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.queue_card, parent, false)
        return queueViewHolder(view)
    }


    override fun getItemCount(): Int = queueList.size

    override fun onBindViewHolder(holder: queueViewHolder, position: Int) {
        val queue = queueList[position]
        val customerName = queue.customerEmail

        holder.date.text = "${queue.date} שעה : ${queue.hour}"

        // אם התור תפוס
        if (customerName.isNotEmpty() || !queue.isAvailable) {
            holder.customerName.text = "תור תפוס"
            holder.addQueueButton.visibility = View.GONE
            holder.cancel.visibility = View.VISIBLE
            holder.cancel.text = "ביטול התור"
            holder.cancel.setOnClickListener {
                cancelQueue(queue, businessEmail)
            }
        } else {
            holder.customerName.text = "תור פנוי"
            holder.addQueueButton.visibility = View.VISIBLE
            holder.cancel.visibility = View.GONE
            holder.addQueueButton.setOnClickListener {
                val customerEmail = FirebaseAuth.getInstance().currentUser?.email ?: return@setOnClickListener
                reserveQueue(queue, customerEmail, businessEmail)
            }
        }
    }

    fun updateQueueList(newQueueList: List<Queue>) {
        queueList.clear()
        queueList.addAll(newQueueList)
        notifyDataSetChanged()
    }
    fun reserveQueue(queue: Queue, customerEmail: String, businessEmail: String) {
        val db = FirebaseFirestore.getInstance()

        db.collection("buissness")
            .document(businessEmail)
            .collection("queues")
            .whereEqualTo("date", queue.date)
            .whereEqualTo("hour", queue.hour)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val doc = documents.documents[0]
                    val isAvailable = doc.getBoolean("isAvailable") ?: true

                    if (!isAvailable) {
                        Toast.makeText(context, "⚠ התור הזה כבר נתפס", Toast.LENGTH_SHORT).show()
                    } else {
                        doc.reference.update(
                            mapOf(
                                "isAvailable" to false,
                                "customerEmail" to customerEmail
                            )
                        ).addOnSuccessListener {
                            queue.customerEmail = customerEmail
                            queue.isAvailable = false
                            notifyDataSetChanged()
                            addQueueToBusinessDocument(queue, businessEmail)
                            Toast.makeText(context, "✅ התור נשמר בהצלחה", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener {
                            Toast.makeText(context, "שגיאה בעדכון", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    val newQueue = hashMapOf(
                        "date" to queue.date,
                        "hour" to queue.hour,
                        "isAvailable" to false,
                        "customerEmail" to customerEmail
                    )
                    db.collection("buissness")
                        .document(businessEmail)
                        .collection("queues")
                        .add(newQueue)
                        .addOnSuccessListener {
                            queue.customerEmail = customerEmail
                            queue.isAvailable = false
                            notifyDataSetChanged()
                            addQueueToBusinessDocument(queue, businessEmail)
                            Toast.makeText(context, "✅ התור נשמר בהצלחה", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "שגיאה ביצירת תור", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "שגיאה בגישה ל-Firebase", Toast.LENGTH_SHORT).show()
            }
    }

    fun addQueueToBusinessDocument(queue: Queue, businessEmail: String) {
        val db = FirebaseFirestore.getInstance()
        val businessDocRef = db.collection("buissness").document(businessEmail)
        businessDocRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                val currentList = document.toObject(Buissnes::class.java)?.queueTaken?.toMutableList() ?: mutableListOf()
                if (currentList.none { it.date == queue.date && it.hour == queue.hour }) {
                    currentList.add(queue)
                    businessDocRef.update("queueTaken", currentList)
                }
            }
        }
    }

    fun cancelQueue(queue: Queue, businessEmail: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("buissness")
            .document(businessEmail)
            .collection("queues")
            .whereEqualTo("date", queue.date)
            .whereEqualTo("hour", queue.hour)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val doc = documents.documents[0]
                    doc.reference.update(
                        mapOf(
                            "isAvailable" to true,
                            "customerEmail" to ""
                        )
                    ).addOnSuccessListener {
                        queue.customerEmail = ""
                        queue.isAvailable = true
                        notifyDataSetChanged()
                        Toast.makeText(context, "התור בוטל בהצלחה", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        Toast.makeText(context, "שגיאה בביטול התור", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "שגיאה בגישה ל-Firebase", Toast.LENGTH_SHORT).show()
            }
    }

}
