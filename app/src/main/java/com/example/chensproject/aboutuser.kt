package com.example.chensproject
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
class aboutuser : AppCompatActivity() {
    lateinit var Name: EditText;
    lateinit var aboutMe: EditText;
    lateinit var portfolio: EditText;
    lateinit var prices: EditText;
    lateinit var submitButton: Button;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.aboutuser)
        Name = findViewById(R.id.Name)
        aboutMe = findViewById(R.id.aboutMe)
        portfolio = findViewById(R.id.portfolio)
        prices = findViewById(R.id.prices)
        submitButton = findViewById(R.id.submitButton)

    }
}
