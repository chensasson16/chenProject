package com.example.chensproject

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BussinesCardAdpater(
    var bussinesnamesList: ArrayList<String>,
    var bussineslocationList: ArrayList<String>,
    var context: Context) : RecyclerView.Adapter<BussinesCardAdpater.BussinesViewHolder>() {

    class BussinesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var textViewBussinesName: TextView =itemView.findViewById(R.id.bussinesName)
        var textViewlocationName: TextView =itemView.findViewById(R.id.locationNames)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BussinesViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: BussinesViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}