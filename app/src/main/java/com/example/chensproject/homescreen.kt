package com.example.chensproject
import android.os.Bundle
import android.widget.EditText
import android.widget.SearchView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class homescreen {class homescreen : AppCompatActivity() {
    lateinit var businessdatabase: TextView;
    lateinit var search: SearchView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.homescreen)
        businessdatabase = findViewById(R.id.businessdatabase)
        search = findViewById(R.id.search)


    }
}
}