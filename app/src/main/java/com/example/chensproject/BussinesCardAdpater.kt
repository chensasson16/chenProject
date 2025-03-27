package com.example.chensproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BusinessCardAdapter(
    private var businessList: List<Buissnes>,
    var context: Context
) : RecyclerView.Adapter<BusinessCardAdapter.BusinessViewHolder>() {

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
        holder.businessName.text = business.getName()
        holder.businessLocation.text = business.getlocation()
    }
}
