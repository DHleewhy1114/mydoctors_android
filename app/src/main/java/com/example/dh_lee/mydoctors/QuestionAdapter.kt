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

class QuestionAdapter(val items: List<QuestionList>, val context: Context,val clickListener: (QuestionList) -> Unit): RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>(){
    override fun getItemCount(): Int {
        return items.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):QuestionViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.questioncomponent, parent,false)
        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        (holder as QuestionViewHolder).bind(items[position],clickListener)
    }

    class QuestionViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(item: QuestionList, clickListener: (QuestionList) -> Unit){
            itemView.questionboardtext?.text = item.question_board_text
            itemView.setOnClickListener{ clickListener(item)}
        }
    }
}