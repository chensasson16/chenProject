package com.example.chensproject

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class BussinesCardAdpater(
    private var businessList: MutableList<Buissnes>,
    private val context: Context
) : RecyclerView.Adapter<BussinesCardAdpater.BusinessViewHolder>() {

    private lateinit var auth: FirebaseAuth

    class BusinessViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Make sure these IDs exactly match what's in your bussinescard.xml layout
        val businessName: TextView = itemView.findViewById(R.id.customerName)
        val businessLocation: TextView = itemView.findViewById(R.id.date)
        val aboutMe: TextView = itemView.findViewById(R.id.cancel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessViewHolder {
        // Use the correct layout file
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bussinescard, parent, false)
        auth = FirebaseAuth.getInstance()
        return BusinessViewHolder(view)
    }

    override fun getItemCount(): Int {
        return businessList.size
    }

    override fun onBindViewHolder(holder: BusinessViewHolder, position: Int) {
        val business = businessList[position]
        holder.businessName.text = business.businessName
        holder.businessLocation.text = business.businessLocation

        // Set click listener on the entire item view for better UX
        holder.aboutMe.setOnClickListener {
            val intent = Intent(context, AboutUser::class.java)
            intent.putExtra("bEmail", business.businessEmail)
            context.startActivity(intent)
        }
    }


    // Update function
    fun updateBusinessList(newBusinessList: List<Buissnes>) {
        businessList.clear()
        businessList.addAll(newBusinessList)
        notifyDataSetChanged()
    }
}