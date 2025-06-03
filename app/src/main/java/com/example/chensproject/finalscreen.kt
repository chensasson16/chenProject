package com.example.chensproject
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class finalscreen : AppCompatActivity() {
    lateinit var finaldate: TextView;
    lateinit var finalhour: TextView;
    lateinit var at: TextView;
    private lateinit var auth: FirebaseAuth
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.finalscreen)
        finaldate = findViewById(R.id.finaldate)
        finalhour = findViewById(R.id.finalhour)
        at = findViewById(R.id.at)

        auth = FirebaseAuth.getInstance()
    }

}