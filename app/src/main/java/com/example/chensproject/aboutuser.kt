package com.example.chensproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AboutUser : AppCompatActivity() {
    lateinit var Name: TextView
    lateinit var aboutMe: TextView
    lateinit var portfolio: TextView
    lateinit var prices: TextView
    lateinit var submitButton: Button
    lateinit var daysAndHours: TextView

    private val db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth
    private var userEmail: String? = null  // Make it a class variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.aboutuser)

        Name = findViewById(R.id.Email)
        aboutMe = findViewById(R.id.aboutMe)
        portfolio = findViewById(R.id.portfolio)
        prices = findViewById(R.id.prices)
        submitButton = findViewById(R.id.submitButton)
        daysAndHours = findViewById(R.id.daysAndHours)

        auth = FirebaseAuth.getInstance()

        // Get the email from intent and add debugging
        userEmail = intent.getStringExtra("bEmail")
        Log.d("AboutUser", "Received bEmail: $userEmail")

        if (userEmail != null && userEmail!!.isNotEmpty()) {
            Log.d("AboutUser", "Loading data for: $userEmail")

            db.collection("buissness").document(userEmail!!)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val name = document.getString("businessName") ?: ""
                        val aboutMeStr = document.getString("aboutMe") ?: ""
                        val portfolioStr = document.getString("portfolio") ?: ""
                        val pricesStr = document.getString("prices") ?: ""
                        val startTime = document.getString("startTime") ?: ""
                        val endTime = document.getString("endTime") ?: ""
                        val daysList = document.get("availableDays") as? List<*> ?: listOf<String>()

                        Name.text = name
                        aboutMe.text = aboutMeStr
                        portfolio.text = portfolioStr
                        prices.text = pricesStr
                        daysAndHours.text = "ימים: ${daysList.joinToString(", ")}\nשעות: $startTime - $endTime"

                        Log.d("AboutUser", "Data loaded successfully for: $name")
                    } else {
                        Log.w("AboutUser", "Document does not exist for: $userEmail")
                        Toast.makeText(this, "לא נמצא מידע על העסק", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("AboutUser", "Error fetching data for: $userEmail", e)
                    Toast.makeText(this, "שגיאה בטעינת המידע", Toast.LENGTH_SHORT).show()
                }
        } else {
            Log.e("AboutUser", "bEmail is null or empty")
            Toast.makeText(this, "שגיאה: לא נמצא מזהה עסק", Toast.LENGTH_SHORT).show()
        }

        submitButton.setOnClickListener {
            Log.d("AboutUser", "Submit button clicked, passing bEmail: $userEmail")

            if (userEmail != null && userEmail!!.isNotEmpty()) {
                val intent = Intent(this, dates::class.java)
                intent.putExtra("bEmail", userEmail)
                startActivity(intent)
            } else {
                Toast.makeText(this, "שגיאה: לא ניתן לעבור לעמוד הבא - חסר מזהה עסק", Toast.LENGTH_SHORT).show()
            }
        }
    }
}