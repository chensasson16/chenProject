package com.example.chensproject

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class BuissnessHomeScreen : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: QueueListAdapter
    private lateinit var db: FirebaseFirestore
    private var bEmail: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.buisness_homescreen)

        recyclerView = findViewById(R.id.busirv)
        recyclerView.layoutManager = LinearLayoutManager(this)

        db = FirebaseFirestore.getInstance()
        bEmail = intent.getStringExtra("bEmail") ?: ""

        if (bEmail.isEmpty()) {
            Toast.makeText(this, "שגיאה: לא הועבר מייל של עסק", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        loadQueueData()
    }

    private fun loadQueueData() {
        db.collection("buissness")
            .document(bEmail)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val queueTaken = document["queueTaken"] as? List<Map<String, Any>>
                    val queueList = queueTaken?.mapNotNull { map ->
                        try {
                            Queue(
                                customerEmail = map["customerEmail"] as? String ?: "",
                                hour = map["hour"] as? String ?: "",
                                date = map["date"] as? String ?: "",
                                isAvailable = map["available"] as? Boolean ?: false
                            )
                        } catch (e: Exception) {
                            null
                        }
                    } ?: emptyList()

                    adapter = QueueListAdapter(queueList.toMutableList(), bEmail)
                    recyclerView.adapter = adapter
                } else {
                    Toast.makeText(this, "לא נמצאו תורים", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "שגיאה בטעינת תורים: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.e("BuissnessHomeScreen", "Error loading queues", e)
            }
    }
}
