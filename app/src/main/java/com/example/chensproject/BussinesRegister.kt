package com.example.chensproject

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class BussinesRegister : AppCompatActivity() {
    private val buisnessRef = Firebase.firestore.collection("buissness")
    private lateinit var auth: FirebaseAuth

    private lateinit var register: Button
    private lateinit var login: TextView
    private lateinit var location: EditText
    private lateinit var startTimeButton: Button
    private lateinit var endTimeButton: Button
    private lateinit var selectedStartTime: String
    private lateinit var selectedEndTime: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.register_bussines)

        auth = FirebaseAuth.getInstance()

        register = findViewById(R.id.registerButton)
        login = findViewById(R.id.login)
        location = findViewById(R.id.Location)
        startTimeButton = findViewById(R.id.startTimeButton)
        endTimeButton = findViewById(R.id.endTimeButton)

        val checkboxes = listOf(
            R.id.checkSunday to "ראשון",
            R.id.checkMonday to "שני",
            R.id.checkTuesday to "שלישי",
            R.id.checkWednesday to "רביעי",
            R.id.checkThursday to "חמישי",
            R.id.checkFriday to "שישי"
        )

        selectedStartTime = ""
        selectedEndTime = ""

        startTimeButton.setOnClickListener {
            TimePickerDialog(this, { _, hour, minute ->
                selectedStartTime = String.format("%02d:%02d", hour, minute)
                startTimeButton.text = "שעת התחלה: $selectedStartTime"
            }, 9, 0, true).show()
        }

        endTimeButton.setOnClickListener {
            TimePickerDialog(this, { _, hour, minute ->
                selectedEndTime = String.format("%02d:%02d", hour, minute)
                endTimeButton.text = "שעת סיום: $selectedEndTime"
            }, 17, 0, true).show()
        }

        register.setOnClickListener {
            val name = findViewById<EditText>(R.id.Name).text.toString()
            val email = findViewById<EditText>(R.id.Email).text.toString()
            val password = findViewById<EditText>(R.id.SignUpPassword).text.toString()
            val aboutMe = findViewById<EditText>(R.id.AboutMe).text.toString()
            val portfolio = findViewById<EditText>(R.id.Portfolio).text.toString()
            val prices = findViewById<EditText>(R.id.Prices).text.toString()

            val selectedDays = checkboxes.mapNotNull { (id, name) ->
                val checkbox = findViewById<CheckBox>(id)
                if (checkbox.isChecked) name else null
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    val business = Buissnes(

                        businessName = name,
                        businessEmail= email,
                        aboutMe = aboutMe,
                        portfolio = portfolio,
                        prices = prices,
                        businessLocation = location.text.toString(),
                        availableDays = selectedDays,
                        startTime = selectedStartTime,
                        endTime = selectedEndTime
                    )
                    saveBusiness(business)
                }
                .addOnFailureListener {
                    Toast.makeText(this, "שגיאה בהרשמה: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }

        login.setOnClickListener {
            startActivity(Intent(this, BussinesLogin::class.java))

        }
    }

    private fun saveBusiness(business: Buissnes) = CoroutineScope(Dispatchers.Main).launch {
        try {
            auth.currentUser?.let {
                buisnessRef.document(it.email!!).set(business).await()
                Toast.makeText(this@BussinesRegister, "העסק נשמר בהצלחה", Toast.LENGTH_LONG).show()
                startActivity(Intent(this@BussinesRegister, BuissnessHomeScreen::class.java))
            }
        } catch (e: Exception) {
            Toast.makeText(this@BussinesRegister, "שגיאה: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
