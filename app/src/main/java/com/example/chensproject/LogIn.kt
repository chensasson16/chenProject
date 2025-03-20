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
import kotlin.math.log

class Login : AppCompatActivity() {
    private val costumerCollectionRef=Firebase.firestore.collection("costumers")

    lateinit var login: Button;
    lateinit var register: TextView;
    private lateinit var auth: FirebaseAuth
     lateinit var tvCustomer:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth
        auth = Firebase.auth
        enableEdgeToEdge()
        setContentView(R.layout.log_in)
         tvCustomer = findViewById(R.id.tvCustomer)
        login = findViewById(R.id.loginButton)
        register = findViewById(R.id.register)
        login.setOnClickListener({
            var Email = findViewById<EditText?>(R.id.Email).text
            var LogInPassword = findViewById<EditText?>(R.id.LogInPassword).text
            auth.signInWithEmailAndPassword(Email.toString(), LogInPassword.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")

                        retrieveCustomer(auth.currentUser?.email.toString())
                        val intent = Intent(this,homescreen::class.java)
                        startActivity(intent)
                        val user = auth.currentUser
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
            Toast.makeText(this,"hi+${Email.toString()}", Toast.LENGTH_SHORT).show()
    })
        register.setOnClickListener({
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
        }

        )



}
    private fun subscribeToRealtimeUpdate(){
        costumerCollectionRef.addSnapshotListener { querySnapshot, firebaseFirestoreException ->  }

    }



    private fun retrieveCustomer(email:String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val querySnapshot = costumerCollectionRef.document(email).get().await()
            auth.currentUser?.let { Log.e(TAG, it.uid) }
            val sb = StringBuilder()
            val document = querySnapshot
            var customer: Customer? = null
            if (document != null) {
                sb.append("${document.get("name")}\n")
                customer = Customer(document.get("name").toString(),document.get("phone").toString(), listOf<Queue>())
            }
            withContext(Dispatchers.Main) {
                tvCustomer.text = sb.toString()
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@Login, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

}