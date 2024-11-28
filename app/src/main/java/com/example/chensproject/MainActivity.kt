package com.example.chensproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var userName:EditText;
    lateinit var phone:EditText;
    lateinit var register:Button;
    lateinit var login:TextView;

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        userName = findViewById(R.id.username)
        phone = findViewById(R.id.phone)
        register = findViewById(R.id.registerButton)
        login = findViewById(R.id.login)
        register.setOnClickListener({
            Toast.makeText(this,"hi+${userName.text}",Toast.LENGTH_SHORT).show()
            val intent = Intent(this,homescreen::class.java)
            startActivity(intent)
        })
    }
}