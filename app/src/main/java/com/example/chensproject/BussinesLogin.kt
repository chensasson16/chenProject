package com.example.chensproject

import android.annotation.SuppressLint
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

class BussinesLogin : AppCompatActivity() {
    private val buisnessrCollectionRef=Firebase.firestore.collection("buissness")



    lateinit var register:Button
    lateinit var login:TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var location: EditText
    private lateinit var tvBuisness: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.bussines_login);



        register = findViewById(R.id.register)
        login = findViewById(R.id.loginButton)


        login.setOnClickListener({
            var Email = findViewById<EditText?>(R.id.email).text
            var login = findViewById<EditText?>(R.id.loginPassword).text

            auth.signInWithEmailAndPassword(Email.toString(), login.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        user?.email?.let { it1 -> retrieveBussines(it1) }
                        val intent = Intent(this, homescreen::class.java)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
            Toast.makeText(this, "hi+${Email.toString()}", Toast.LENGTH_SHORT).show()


            // שליפת נתוני המשתמש מתוך מסמך ה-Firebase


        })
    }
    fun retrieveBussines(email: String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val document = buisnessrCollectionRef.document(email).get().await()
            val customer = document.toObject<Customer>()

            withContext(Dispatchers.Main) {
                customer?.let {
                    tvBuisness.text = "Name: ${it.name}\nPhone: ${it.phone}"
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@BussinesLogin, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }


    // מאזין לשינויים בנתוני המשתמש המחובר בלבד
    fun subscribeToRealtimeUpdate() {
        val userEmail = auth.currentUser?.email ?: return

        buisnessrCollectionRef.document(userEmail)
            .addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Toast.makeText(this, firebaseFirestoreException.message, Toast.LENGTH_LONG).show()
                    return@addSnapshotListener
                }

                documentSnapshot?.let {
                    val customer = it.toObject<Customer>()
                    if (customer != null) {
                        runOnUiThread {
                            tvBuisness.text = "Name: ${customer.name}\nPhone: ${customer.phone}"
                        }
                    }
                }
            }
    }
    }



