package com.example.chensproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var email:String;
    lateinit var SignUpPassword:String;
    lateinit var register:Button;
    lateinit var login:TextView;
    lateinit var mAuth: FirebaseAuth;

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        register = findViewById(R.id.registerButton)
        login = findViewById(R.id.login)
        mAuth = FirebaseAuth.getInstance()

        login.setOnClickListener {
            val intent = Intent(this, LogIn::class.java)
            startActivity(intent)
        }



        register.setOnClickListener{
            email = findViewById<EditText>(R.id.email).text.toString()
            SignUpPassword = findViewById<EditText>(R.id.SignUpPassword).text.toString()

            Toast.makeText(this,"hi+${email.toString()}",Toast.LENGTH_SHORT).show()

            mAuth.createUserWithEmailAndPassword(email, SignUpPassword)
                .addOnCompleteListener{task->
                    if (task.isSuccessful) {
                        val intent = Intent(this, LogIn::class.java)
                        startActivity(intent)
                    }
                    else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                }
        }



    }


}
}