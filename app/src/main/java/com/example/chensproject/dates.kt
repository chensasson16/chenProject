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
import java.util.*

class dates : AppCompatActivity() {
    private lateinit var calendarView: CalendarView
    private lateinit var dayText: TextView
    private lateinit var rv: RecyclerView
    private lateinit var adapter: QueueCardAdpater
    private lateinit var userEmail: String

    private lateinit var businessName: String
    private lateinit var aboutMe: String
    private lateinit var portfolio: String
    private lateinit var prices: String
    private lateinit var startTime: String
    private lateinit var endTime: String
    private var availableDays: List<String> = listOf()
    private var queueList = mutableListOf<Queue>()

    private val db = FirebaseFirestore.getInstance()

    private val daysMap = mapOf(
        Calendar.SUNDAY to "יום ראשון",
        Calendar.MONDAY to "יום שני",
        Calendar.TUESDAY to "יום שלישי",
        Calendar.WEDNESDAY to "יום רביעי",
        Calendar.THURSDAY to "יום חמישי",
        Calendar.FRIDAY to "יום שישי",
        Calendar.SATURDAY to "שבת"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.dates)

        calendarView = findViewById(R.id.calendar)
        dayText = findViewById(R.id.dayOfWeekText)
        rv = findViewById(R.id.hoursRecyclerView)
        rv.layoutManager = LinearLayoutManager(this)

        // 🛠️ כאן מוגדר המשתמש הראשי (ולא כ-val בתוך הפונקציה)
        userEmail = intent.getStringExtra("bEmail") ?: ""

        adapter = QueueCardAdpater(queueList, this, userEmail)
        rv.adapter = adapter

        if (userEmail.isNotEmpty()) {
            db.collection("buissness").document(userEmail)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        businessName = document.getString("businessName") ?: ""
                        aboutMe = document.getString("aboutMe") ?: ""
                        portfolio = document.getString("portfolio") ?: ""
                        prices = document.getString("prices") ?: ""
                        startTime = document.getString("startTime") ?: "09:00"
                        endTime = document.getString("endTime") ?: "19:00"
                        val days = document.get("availableDays")
                        availableDays = if (days is List<*>) {
                            days.filterIsInstance<String>()
                        } else {
                            listOf()
                        }
                        val dayNameMap = mapOf(
                            "ראשון" to "יום ראשון",
                            "שני" to "יום שני",
                            "שלישי" to "יום שלישי",
                            "רביעי" to "יום רביעי",
                            "חמישי" to "יום חמישי",
                            "שישי" to "יום שישי",
                            "שבת" to "שבת"
                        )
                        availableDays = availableDays.map { dayNameMap[it] ?: it }
                        val queues = document.get("queueTaken")
                        queueList.clear()
                        if (queues is List<*>) {
                            for (item in queues) {
                                if (item is Map<*, *>) {
                                    val customerEmail = item["customerEmail"] as? String ?: ""
                                    val hour = item["hour"] as? String ?: ""
                                    val date = item["date"] as? String ?: ""
                                    val isAvailable = item["isAvailable"] as? Boolean ?: true
                                    queueList.add(Queue(customerEmail, hour, date, isAvailable))
                                }
                            }
                        }

                        setupCalendar()
                        // הצג תורים של היום הנוכחי אוטומטית
                        val calendar = Calendar.getInstance()
                        val year = calendar.get(Calendar.YEAR)
                        val month = calendar.get(Calendar.MONTH)
                        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
                        calendarView.date = calendar.timeInMillis
                        // הפעל את ה-Listener ידנית
                        calendarView.post {
                            calendarView.setDate(calendar.timeInMillis, false, true)
                            // הפעל את הלוגיקה של הצגת התורים ליום הנוכחי
                            val dayName = daysMap[calendar.get(Calendar.DAY_OF_WEEK)] ?: ""
                            dayText.text = "יום השבוע: $dayName"
                            val takenForDay = queueList.filter { it.date == dayName }
                            queueList.clear()
                            if (availableDays.contains(dayName)) {
                                val startHour = startTime.replace(":00", "").toIntOrNull() ?: 9
                                val endHour = endTime.replace(":00", "").toIntOrNull() ?: 19
                                for (i in startHour until endHour) {
                                    val hourStr = "$i:00"
                                    val existing = takenForDay.find { it.hour == hourStr }
                                    if (existing != null) {
                                        queueList.add(existing)
                                    } else {
                                        queueList.add(Queue("", hourStr, dayName, true))
                                    }
                                }
                                adapter.notifyDataSetChanged()
                            } else {
                                adapter.notifyDataSetChanged()
                            }
                        }
                    } else {
                        Toast.makeText(this, "לא נמצא מידע על העסק", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("dates", "Error fetching data", e)
                    Toast.makeText(this, "שגיאה בטעינת המידע", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "לא הועבר מזהה עסק", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupCalendar() {
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)

            val dayName = daysMap[calendar.get(Calendar.DAY_OF_WEEK)] ?: ""
            dayText.text = "יום השבוע: $dayName"

            // תורים קיימים ליום הזה (מתוך queueTaken)
            val takenForDay = queueList.filter { it.date == dayName }
            val takenHours = takenForDay.map { it.hour }

            queueList.clear()

            if (availableDays.contains(dayName)) {
                val startHour = startTime.replace(":00", "").toIntOrNull() ?: 9
                val endHour = endTime.replace(":00", "").toIntOrNull() ?: 19

                for (i in startHour until endHour) {
                    val hourStr = "$i:00"
                    val existing = takenForDay.find { it.hour == hourStr }
                    if (existing != null) {
                        queueList.add(existing)
                    } else {
                        queueList.add(Queue("", hourStr, dayName, true))
                    }
                }
                adapter.notifyDataSetChanged()
            } else {
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "העסק לא פעיל ביום הזה", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
