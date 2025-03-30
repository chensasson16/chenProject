package com.example.chensproject

import Buissnes
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
class BussinesCardAdpater(
    var businessList: MutableList<Buissnes>, // שנה ל-MutableList אם אתה מעדכן את הרשימה
    var context: Context
) : RecyclerView.Adapter<BussinesCardAdpater.BusinessViewHolder>() {

    class BusinessViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val businessName: TextView = itemView.findViewById(R.id.bussinesName)
        val businessLocation: TextView = itemView.findViewById(R.id.locationNames)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.bussinescard, parent, false)
        return BusinessViewHolder(view)
    }

    override fun getItemCount(): Int {
        return businessList.size
    }

    override fun onBindViewHolder(holder: BusinessViewHolder, position: Int) {
        val business = businessList[position]
        holder.businessName.text = business.name // אם השדה 'name' הוא ציבורי
        holder.businessLocation.text = business.location // אם השדה 'location' הוא ציבורי
    }

    // פונקציה לעדכון הרשימה במקרה שצריך להוסיף או לעדכן נתונים
    fun updateBusinessList(newBusinessList: List<Buissnes>) {
        businessList.clear() // לנקות את הרשימה הקיימת
        businessList.addAll(newBusinessList) // להוסיף את הרשימה החדשה
        notifyDataSetChanged() // לעדכן את ה-RecyclerView
    }
}
