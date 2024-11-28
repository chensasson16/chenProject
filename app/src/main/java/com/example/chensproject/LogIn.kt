package com.example.chensproject

import android.os.Bundle
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
    lateinit var userName: EditText;
    lateinit var phone: EditText;
    lateinit var login: Button;
    lateinit var register: TextView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.log_in)
        userName = findViewById(R.id.username)
        phone = findViewById(R.id.phone)
        login = findViewById(R.id.loginButton)
            login.setOnClickListener()
        register = findViewById(R.id.register)
        login.setOnClickListener({
            Toast.makeText(this,"hi+${userName.text}", Toast.LENGTH_SHORT).show()
    })
}
}