package com.example.dh_lee.mydoctors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.relationship_component.view.*
import android.content.Context
import android.widget.AdapterView
import kotlinx.android.synthetic.main.hospitalcomponent.view.*

class HospitalAdapter(val items: List<HospitalData>, val context: Context,val clickListener: (HospitalData) -> Unit): RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder>(){
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):HospitalViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.hospitalcomponent, parent,false)
        return HospitalViewHolder(view)
    }

    override fun onBindViewHolder(holder: HospitalViewHolder, position: Int) {
        (holder as HospitalViewHolder).bind(items[position],clickListener)
    }

    class HospitalViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(item: HospitalData, clickListener: (HospitalData) -> Unit){
            itemView.hospital_name.text = item.hospital_name
            itemView.hospital_info.text = item.hospital_info
            itemView.setOnClickListener{ clickListener(item)}
        }
    }
}