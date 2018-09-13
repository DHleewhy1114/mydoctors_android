package com.example.dh_lee.mydoctors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.relationship_component.view.*
import android.content.Context
import android.widget.AdapterView
import android.R.attr.data
import android.text.method.TextKeyListener.clear

class RelationshipAdapter(val items: List<DoctorData>, val context: Context,val clickListener: (DoctorData) -> Unit): RecyclerView.Adapter<RelationshipAdapter.RelationshipViewHolder>(){
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):RelationshipViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.relationship_component, parent,false)
        return RelationshipViewHolder(view)
    }

    override fun onBindViewHolder(holder: RelationshipViewHolder, position: Int) {
        (holder as RelationshipViewHolder).bind(items[position],clickListener)
    }

    class RelationshipViewHolder(view: View): RecyclerView.ViewHolder(view){
        val doctorname = view.doctorname
        fun bind(item: DoctorData, clickListener: (DoctorData) -> Unit){
            itemView.doctorname.text = item.doctor_name
            itemView.setOnClickListener{ clickListener(item)}
        }
    }
}