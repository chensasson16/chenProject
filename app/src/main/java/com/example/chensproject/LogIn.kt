package com.example.chensproject

import android.content.ContentValues.TAG
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
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

class Login : AppCompatActivity() {

    lateinit var login: Button;
    lateinit var register: TextView;
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth
        auth = Firebase.auth
        enableEdgeToEdge()
        setContentView(R.layout.log_in)

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


}


}