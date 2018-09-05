package com.example.dh_lee.mydoctors

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.chipcomponent.view.*

class ChipAdapter(val items: ArrayList<String>, val context: Context): RecyclerView.Adapter<ChipAdapter.ChipViewHolder>(){
    interface onClickListener
    override fun getItemCount(): Int {
        return items.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ChipViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.chipcomponent, parent,false)
        return ChipViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChipViewHolder, position: Int) {
        holder.todoctor.text = items[position]
    }

    class ChipViewHolder(view: View): RecyclerView.ViewHolder(view){
        val todoctor = view.doctorsend
    }
}
