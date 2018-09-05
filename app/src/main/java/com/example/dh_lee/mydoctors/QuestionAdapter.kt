package com.example.dh_lee.mydoctors


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.relationship_component.view.*
import android.content.Context
import android.widget.AdapterView
import kotlinx.android.synthetic.main.questioncomponent.view.*

class QuestionList(val question_board_text:String)
class QuestionAdapter(val items: List<QuestionList>, val context: Context,val clickListener: (QuestionList) -> Unit): RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>(){
    //private val mOnClickListener = onclick()
    //interface onClickListener
    override fun getItemCount(): Int {
        return items.size
    }
    /*fun onClick(view:View,position:Int){
        val item = items[position]
        Log.e("positionclick",items[position].toString())
        val intent = Intent(QuestionActivity::class.java)
        startActivity(intent)
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):QuestionViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.questioncomponent, parent,false)
        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        (holder as QuestionViewHolder).bind(items[position],clickListener)
        //holder.itemView.setOnClickListener { onClick(view,position) }
        //Log.e("itemView",holder.itemView.toString())
        //holder.itemView.setOnClickListener{onclick(position)}
    }

    class QuestionViewHolder(view: View): RecyclerView.ViewHolder(view){
        //val doctorname = view.doctorname
        //val doctorcard = view.findViewById(R.id.doctor_card) as MaterialCardView
        fun bind(item: QuestionList, clickListener: (QuestionList) -> Unit){
            itemView.questionboardtext?.text = item.question_board_text
            itemView.setOnClickListener{ clickListener(item)}
        }
/* fun bind(part: PartData, clickListener: (PartData) -> Unit) {
        itemView.tv_part_item_name.text = part.itemName
        itemView.tv_part_id.text = part.id.toString()
        itemView.setOnClickListener { clickListener(part)}
    }*/
    }
}