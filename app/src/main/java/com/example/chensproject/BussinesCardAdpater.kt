package com.example.chensproject

import Buissnes
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BussinesCardAdpater(
    private var businessList: MutableList<Buissnes>,
    private val context: Context
) : RecyclerView.Adapter<BussinesCardAdpater.BusinessViewHolder>() {

    class BusinessViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Make sure these IDs exactly match what's in your bussinescard.xml layout
        val businessName: TextView = itemView.findViewById(R.id.bussinesName)
        val businessLocation: TextView = itemView.findViewById(R.id.locationNames)
        val aboutMe: TextView = itemView.findViewById(R.id.toAboutme)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessViewHolder {
        // Use the correct layout file
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bussinescard, parent, false)
        return BusinessViewHolder(view)
    }

    override fun getItemCount(): Int {
        return businessList.size
    }

    override fun onBindViewHolder(holder: BusinessViewHolder, position: Int) {
        val business = businessList[position]
        holder.businessName.text = business.name
        holder.businessLocation.text = business.location

        // Set click listener on the entire item view for better UX
        holder.aboutMe.setOnClickListener {
            val intent = Intent(context, AboutUser::class.java)
            intent.putExtra("name", business.name)
            intent.putExtra("location", business.location)
            context.startActivity(intent)
        }
    }

    holder.submitButton.setOnClickListener {
        val intent = Intent(context, AboutUser::class.java)
        intent.putExtra("name", business.name)
        intent.putExtra("location", business.location)
        context.startActivity(intent)
    }

    // Update function
    fun updateBusinessList(newBusinessList: List<Buissnes>) {
        businessList.clear()
        businessList.addAll(newBusinessList)
        notifyDataSetChanged()
    }
}