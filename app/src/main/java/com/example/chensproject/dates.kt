package com.example.chensproject

import android.os.Bundle
import android.util.Log
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class dates : AppCompatActivity() {
    private lateinit var calendarView: CalendarView
    private lateinit var dayText: TextView
    lateinit var rv: RecyclerView
    lateinit var adapter: QueueCardAdpater

    private lateinit var businessName: String
    private lateinit var aboutMe: String
    private lateinit var portfolio: String
    private lateinit var prices: String
    private lateinit var startTime: String
    private lateinit var endTime: String
    private  var availableDays: List<String> = listOf()
    private  var queueList =  mutableListOf<Queue>()

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.dates)

        calendarView = findViewById(R.id.calendar)
        dayText = findViewById(R.id.dayOfWeekText)

        val userEmail = intent.getStringExtra("bEmail")

        if (userEmail != null) {
            db.collection("buissness").document(userEmail)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        businessName = document.getString("businessName") ?: ""
                        aboutMe = document.getString("aboutMe") ?: ""
                        portfolio = document.getString("portfolio") ?: ""
                        prices = document.getString("prices") ?: ""
                        startTime = document.getString("startTime") ?: ""
                        endTime = document.getString("endTime") ?: ""
                        availableDays = document.get("availableDays") as? List<String> ?: listOf()
                    } else {
                        Toast.makeText(this, "לא נמצא מידע על העסק", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("dates", "Error fetching data", e)
                    Toast.makeText(this, "שגיאה בטעינת המידע", Toast.LENGTH_SHORT).show()
                }
        }
        rv = findViewById(R.id.hoursRecyclerView)

        rv.layoutManager = LinearLayoutManager(this@dates)

        // אתחול של ה-Adapter
        adapter = QueueCardAdpater(queueList, this@dates)
        rv.adapter = adapter
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)

            val dayName = SimpleDateFormat("EEEE", Locale("he", "IL")).format(calendar.time)
            dayText.text = "יום השבוע: $dayName"

            // כאן תוכל לבדוק אם היום הזה נמצא ב-availableDays ולהציג תורים וכו'
            if(availableDays.contains(dayName)){
                for(i in startTime.toInt()..endTime.toInt()){
                  queueList.add(Queue(null,i.toString(),dayName,null))
                }
                adapter.notifyDataSetChanged() // חשוב לעדכן את ה-RecyclerView לאחר שמוסיף נתונים חדשים

            }
        }
    }
}
