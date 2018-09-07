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
    //private val mOnClickListener = onclick()
    //interface onClickListener
    //private var mlist:List<ItemData>?=null
    override fun getItemCount(): Int {
        return items.size
    }
    /*fun onClick(view:View,position:Int){
        val item = items[position]
        Log.e("positionclick",items[position].toString())
        val intent = Intent(QuestionActivity::class.java)
        startActivity(intent)
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):RelationshipViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.relationship_component, parent,false)
        return RelationshipViewHolder(view)
    }

    override fun onBindViewHolder(holder: RelationshipViewHolder, position: Int) {
        (holder as RelationshipViewHolder).bind(items[position],clickListener)
        //holder.itemView.setOnClickListener { onClick(view,position) }
        //Log.e("itemView",holder.itemView.toString())
        //holder.itemView.setOnClickListener{onclick(position)}
    }

    class RelationshipViewHolder(view: View): RecyclerView.ViewHolder(view){
        val doctorname = view.doctorname
        //val doctorcard = view.findViewById(R.id.doctor_card) as MaterialCardView
        fun bind(item: DoctorData, clickListener: (DoctorData) -> Unit){
            itemView.doctorname.text = item.doctor_name
            itemView.setOnClickListener{ clickListener(item)}
        }
/* fun bind(part: PartData, clickListener: (PartData) -> Unit) {
        itemView.tv_part_item_name.text = part.itemName
        itemView.tv_part_id.text = part.id.toString()
        itemView.setOnClickListener { clickListener(part)}
    }*/
    }

    /*fun update(list:List<ItemData>) {
    this.mlist = list
        notifyDataSetChanged()
    }*/
}