package com.example.chensproject
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class AboutUser : AppCompatActivity() {
    lateinit var Name: TextView;
    lateinit var aboutMe: TextView;
    lateinit var portfolio: TextView;
    lateinit var prices: TextView;
    lateinit var submitButton: Button;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.aboutuser)


        Name = findViewById(R.id.Email)
        Name.text = intent.getStringExtra("name")
        aboutMe = findViewById(R.id.aboutMe)
        aboutMe.text = intent.getStringExtra("location")
        portfolio = findViewById(R.id.portfolio)
        prices = findViewById(R.id.prices)
        submitButton = findViewById(R.id.submitButton)

    }
}
