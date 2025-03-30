package com.example.chensproject

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class Login : AppCompatActivity() {
    private val costumerCollectionRef = Firebase.firestore.collection("costumers")
    private lateinit var auth: FirebaseAuth

    private lateinit var login: Button
    private lateinit var register: TextView
    private lateinit var tvCustomer: TextView
    private lateinit var tvBuissnes: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        enableEdgeToEdge()
        setContentView(R.layout.log_in)

        tvCustomer = findViewById(R.id.tvCustomer)
        login = findViewById(R.id.loginButton)
        register = findViewById(R.id.register)

        login.setOnClickListener {
            val email = findViewById<EditText>(R.id.Email).text.toString()
            val password = findViewById<EditText>(R.id.LogInPassword).text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithEmail:success")

                        retrieveCustomer(email)  // שליפת נתוני המשתמש
                        subscribeToRealtimeUpdate()  // מאזין לעדכונים בזמן אמת

                        val intent = Intent(this, homescreen::class.java)
                        startActivity(intent)
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            this, "Authentication failed.", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        register.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    // מאזין לשינויים בנתוני המשתמש המחובר בלבד
    private fun subscribeToRealtimeUpdate() {
        val userEmail = auth.currentUser?.email ?: return

        costumerCollectionRef.document(userEmail)
            .addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Toast.makeText(this, firebaseFirestoreException.message, Toast.LENGTH_LONG).show()
                    return@addSnapshotListener
                }

                documentSnapshot?.let {
                    val customer = it.toObject<Customer>()
                    if (customer != null) {
                        runOnUiThread {
                            tvCustomer.text = "Name: ${customer.name}\nPhone: ${customer.phone}"
                        }
                    }
                }
            }
    }

    // שליפת נתוני המשתמש מתוך מסמך ה-Firebase
    private fun retrieveCustomer(email: String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val document = costumerCollectionRef.document(email).get().await()
            val customer = document.toObject<Customer>()

            withContext(Dispatchers.Main) {
                customer?.let {
                    tvCustomer.text = "Name: ${it.name}\nPhone: ${it.phone}"
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@Login, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }









}
