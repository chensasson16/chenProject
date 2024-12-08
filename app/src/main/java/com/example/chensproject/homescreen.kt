package com.example.chensproject
import android.os.Bundle
import android.widget.EditText
import android.widget.SearchView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class homescreen : AppCompatActivity() {
    lateinit var businessdatabase: TextView;
    lateinit var rv:RecyclerView;
    var bussinesnamesList=ArrayList<String>();
    var bussineslocationList=ArrayList<String>();
    lateinit var search: SearchView;
    lateinit var adapter : BussinesCardAdpater


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.homescreen)
        businessdatabase = findViewById(R.id.businessdatabase)
        search = findViewById(R.id.search)
        rv=findViewById(R.id.busirv);
        rv.layoutManager=LinearLayoutManager(this@homescreen)
        bussinesnamesList.add("נעמה ניילס")
        bussinesnamesList.add("אריאלה ביטון")
        bussinesnamesList.add("שירה ציפורניים")

        bussineslocationList.add("אשדוד")
        bussineslocationList.add("שדרות")
        bussineslocationList.add("אשקלון")



        adapter = BussinesCardAdpater(bussinesnamesList,bussineslocationList,this@homescreen)

        rv.adapter= adapter
    }
}
