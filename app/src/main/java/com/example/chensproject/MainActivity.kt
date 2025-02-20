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
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private val costumerCollectionRef=Firebase.firestore.collection("costumers")



    lateinit var register:Button;
    lateinit var login:TextView;
    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth

        fun reload() {
            TODO("Not yet implemented")
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        register = findViewById(R.id.registerButton)
        login = findViewById(R.id.login)
        register.setOnClickListener({
            var Email = findViewById<EditText?>(R.id.Email).text
            var SignUpPassword = findViewById<EditText?>(R.id.SignUpPassword).text
            auth.createUserWithEmailAndPassword(Email.toString(), SignUpPassword.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        private fun saveCostumer(customer: Customer)= CoroutineScope(Dispatchers.IO).launch {
                            try {
                                costumerCollectionRef.add()
                            } catch (e: Exception){
                                withContext(Dispatchers.Main){
                                    Toast.makeText(this@MainActivity, e.message,Toast.LENGTH_LONG).show()
                                }
                            }
                        }

                        val intent = Intent(this,homescreen::class.java)
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
            Toast.makeText(this,"hi+${Email.toString()}",Toast.LENGTH_SHORT).show()

        })
        login.setOnClickListener({
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
        })





    }

}