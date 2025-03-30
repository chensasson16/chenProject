package com.example.chensproject

import Buissnes
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class BussinesRegister : AppCompatActivity() {
    private val buisnessrCollectionRef=Firebase.firestore.collection("buissness")



    lateinit var register:Button
    lateinit var login:TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var location: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth

        fun reload() {
            TODO("Not yet implemented")
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.register_bussines);



        register = findViewById(R.id.registerButton)
        login = findViewById(R.id.login)

        register.setOnClickListener({
            var Name= findViewById<EditText>(R.id.Name).text
            var Email = findViewById<EditText?>(R.id.Email).text
            var SignUpPassword = findViewById<EditText?>(R.id.SignUpPassword).text
            var queueList =   mutableListOf<Queue>()
            var customerList =   mutableListOf<Customer>()
            location = findViewById<EditText>(R.id.Location)


            auth.createUserWithEmailAndPassword(Email.toString(), SignUpPassword.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser


                        val buissnes= Buissnes(Name.toString(),"MySite","100",
                            location.text.toString()
                        )

                        saveBuisness(buissnes)

                        val intent = Intent(this,homescreen::class.java)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
            Toast.makeText(this,"hi+${Email.toString()}",Toast.LENGTH_SHORT).show()

        })
        login.setOnClickListener({
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
        })
        fun fetchBusinesses(onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
            buisnessrCollectionRef.get()
                .addOnSuccessListener { documents ->
                    val businesses = mutableListOf<Buissnes>()
                    for (document in documents) {
                        val name = document.getString("name") ?: "Unknown"
                        val location = document.getString("location") ?: "Unknown"
                        businesses.add(Buissnes(name,"" ,"",location))
                    }
                    val businessList = mutableListOf<Buissnes>()
                    businessList.clear()
                    businessList.addAll(businesses)
                //    notifyDataSetChanged()
                    onSuccess()
                }
                .addOnFailureListener { exception ->
                    Log.e("BusinessCardAdapter", "Error fetching businesses", exception)
                    onFailure(exception)
                }
        }





    }

    private fun saveBuisness(buissnes: Buissnes) = CoroutineScope(Dispatchers.Main).launch {
        try {
            auth.currentUser?.let { buisnessrCollectionRef.document(it.email.toString()).set(buissnes).await() }
            withContext(Dispatchers.Main) {
                Toast.makeText(this@BussinesRegister, "SUCCESSFULLY SAVED DATA ${auth.currentUser?.uid}", Toast.LENGTH_LONG)
                    .show()
            }
        }
        catch (e:Exception)
        {
            withContext(Dispatchers.Main)
            {
                Toast.makeText(this@BussinesRegister, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}
