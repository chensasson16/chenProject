package com.example.chensproject

import android.os.Bundle
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import com.google.firebase.firestore.FieldValue

class dates : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private var availableDays: List<String> = emptyList()
    private var bEmail: String = ""
    private var endTime: String = "17:00"
    private var selectedDateString: String = ""
    private lateinit var adapter: HoursAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dates)

        db = FirebaseFirestore.getInstance()

        val calendarView = findViewById<CalendarView>(R.id.calendar)
        val dayOfWeekText = findViewById<TextView>(R.id.dayOfWeekText)

        bEmail = intent.getStringExtra("bEmail") ?: ""

        if (bEmail.isNotEmpty()) {
            loadBusinessAvailability()
        } else {
            Toast.makeText(this, "שגיאה: לא נמצא מזהה עסק", Toast.LENGTH_SHORT).show()
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)

            val sdf = SimpleDateFormat("EEEE", Locale("he"))
            val dayOfWeek = sdf.format(calendar.time)
            dayOfWeekText.text = "יום השבוע: $dayOfWeek"

            selectedDateString = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
            checkDayAvailability(dayOfWeek)
        }
    }

    private fun loadBusinessAvailability() {
        db.collection("buissness")
            .document(bEmail)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    availableDays = document.get("availableDays") as? List<String> ?: emptyList()
                    endTime = document.getString("endTime") ?: "17:00"
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "שגיאה בטעינת נתונים", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkDayAvailability(selectedDay: String) {
        if (availableDays.contains(selectedDay)) {
            val availableHours = generateAvailableHours()

            db.collection("buissness")
                .document(bEmail)
                .collection("queues")
                .whereEqualTo("date", selectedDateString)
                .get()
                .addOnSuccessListener { documents ->
                    val takenHours = documents.mapNotNull { it.getString("hour") }
                    val freeHours = availableHours.filter { hour -> hour !in takenHours }
                    showAvailableHours(freeHours)
                }
                .addOnFailureListener {
                    Toast.makeText(this, "שגיאה בטעינת תורים", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "❌ $selectedDay לא זמין", Toast.LENGTH_SHORT).show()
        }
    }

    private fun generateAvailableHours(): List<String> {
        val startHour = 9
        val endHour = endTime.split(":")[0].toIntOrNull() ?: 17

        val hours = mutableListOf<String>()
        for (hour in startHour until endHour) {
            hours.add(String.format("%02d:00", hour))
        }
        return hours
    }

    private fun showAvailableHours(hours: List<String>) {
        val recyclerView = findViewById<RecyclerView>(R.id.hoursRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)


        val mutableHours = hours.toMutableList()

         adapter = HoursAdapter(mutableHours) { selectedHour, position ->
            val customerEmail = FirebaseAuth.getInstance().currentUser?.email ?: return@HoursAdapter

            // בדיקה שהתור תפוס
            db.collection("buissness")
                .document(bEmail)
                .collection("queues")
                .whereEqualTo("date", selectedDateString)
                .whereEqualTo("hour", selectedHour)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        Toast.makeText(this, "❌ השעה הזו כבר נתפסה", Toast.LENGTH_SHORT).show()
                        return@addOnSuccessListener
                    }

                    val queue = Queue(
                        customerEmail = customerEmail,
                        hour = selectedHour,
                        date = selectedDateString,
                        isAvailable = false
                    )

                    // שמירה ל-Firebase
                    db.collection("buissness")
                        .document(bEmail)
                        .collection("queues")
                        .add(queue)
                        .addOnSuccessListener {
                            val businessDocRef = db.collection("buissness").document(bEmail)
                            businessDocRef.update("queueTaken", FieldValue.arrayUnion(queue))

                            val costumerDocRef = db.collection("costumers").document(customerEmail)
                            costumerDocRef.update("queueList", FieldValue.arrayUnion(queue))

                            Toast.makeText(this, "✅ נקבע תור ב-$selectedHour", Toast.LENGTH_SHORT).show()

                            // 🔥 הסרת השעה שנקבעה מהרשימה
                            adapter.removeHourAt(position)
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "❌ שגיאה בקביעת תור", Toast.LENGTH_SHORT).show()
                        }
                }
        }

        recyclerView.adapter = adapter
    }

}
