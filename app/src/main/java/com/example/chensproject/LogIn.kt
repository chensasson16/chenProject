package com.example.chensproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LogIn : AppCompatActivity() {
    private lateinit var email: EditText
    private lateinit var logInPassword: EditText
    private lateinit var loginButton: Button
    private lateinit var registerTextView: TextView
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.log_in)

        // Initialize FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance()

        // Find views by their IDs
        email = findViewById(R.id.email)
        logInPassword = findViewById(R.id.LogInPassword)
        loginButton = findViewById(R.id.loginButton)
        registerTextView = findViewById(R.id.register)




        // Set onClickListener for the login button
        loginButton.setOnClickListener {
            val emailText = email.text.toString().trim()
            val passwordText = logInPassword.text.toString().trim()

            // Validate input
            if (emailText.isEmpty() || passwordText.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            // Sign in with Firebase Authentication
            mAuth.signInWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Navigate to HomeScreen
                        val intent = Intent(this, homescreen::class.java)
                        startActivity(intent)
                        finish() // Close the login screen
                    } else {
                        // Display an error message
                        Log.w("TAG", "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            this,
                            "Authentication failed: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }


        registerTextView.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        // Set onClickListener for the register text



























    }
}
