package com.example.chensproject
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
class setdate : AppCompatActivity() {
    lateinit var date: TextView;
    lateinit var freehours: TextView;
    lateinit var hour1: Button;
    lateinit var hour2: Button;

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.setdate)
        date = findViewById(R.id.date)
        freehours = findViewById(R.id.freehours)
        hour1 = findViewById(R.id.hour1)
        hour2 = findViewById(R.id.hour2)

    }
}