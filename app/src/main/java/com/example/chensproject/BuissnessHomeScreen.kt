package com.example.chensproject
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
class BuissnessHomeScreen : AppCompatActivity() {
    lateinit var businessdatabase: TextView
    lateinit var rv: RecyclerView
    private var queueList = mutableListOf<Queue>() // רשימה אחת עבור העסקים
    private val bussinesCollectionRef = Firebase.firestore.collection("buissness")
    private lateinit var auth: FirebaseAuth

    lateinit var search: SearchView
    lateinit var adapter: QueueCardAdpater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.buisness_homescreen)

        auth = Firebase.auth
        // אתחול של ה-UI
        rv = findViewById(R.id.busirv)

        rv.layoutManager = LinearLayoutManager(this@BuissnessHomeScreen)

        // אתחול של ה-Adapter
        adapter = QueueCardAdpater(queueList, this@BuissnessHomeScreen)
        rv.adapter = adapter

        // קריאה לפונקציה לשליפת העסקים
        retrieveBuissnes()
    }

    // שליפת העסקים מ-Firestore
    private fun retrieveBuissnes() = CoroutineScope(Dispatchers.IO).launch {
        try {
            // שליפת כל המסמכים ממסד הנתונים
            val documents = bussinesCollectionRef.document(auth.currentUser!!.email.toString()).get().await()
            Log.d(TAG, " aa ${documents}")

            withContext(Dispatchers.Main) {
                    val buissnes =
                        documents.toObject<Buissnes>() // המרת המסמך לאובייקט מסוג Buissnes

                    buissnes?.let {
                        // הוספת העסק שנמצא לרשימה
                        queueList = (it.queueTaken).toMutableList()
                    }
                }

                // עדכון ה-RecyclerView
                adapter.notifyDataSetChanged() // חשוב לעדכן את ה-RecyclerView לאחר שמוסיף נתונים חדשים

        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                // הצגת שגיאה אם יש בעיה בטעינת הנתונים
                Toast.makeText(this@BuissnessHomeScreen, e.message, Toast.LENGTH_LONG).show()
                Log.d(TAG, " aa ${e}")

            }
        }

    }
}
