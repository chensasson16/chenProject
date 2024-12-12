package com.example.chensproject

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LogIn : AppCompatActivity() {
    lateinit var email: EditText;
    lateinit var phone: EditText;
    lateinit var login: Button;
    lateinit var register: TextView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.log_in)
        email = findViewById(R.id.email)
        phone = findViewById(R.id.phone)
        login = findViewById(R.id.loginButton)
        //login.setOnClickListener()
        register = findViewById(R.id.register)
        login.setOnClickListener({
            Toast.makeText(this,"hi+${email.text}", Toast.LENGTH_SHORT).show()
    })
}
}