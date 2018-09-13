package com.example.dh_lee.mydoctors

import android.content.Intent
import android.content.Intent.getIntent
import android.content.Intent.getIntentOld
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import mutations.MakeRelationshipMutation
import queries.QuestionByUserQuery
import queries.QuestionQuery

class AnswerActivity: BottomSheetDialogFragment() {
    var question_id: String = ""
    private lateinit var question_text_view: TextView
    private lateinit var answer_text_view: TextView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.question_answer, container,
                false)
        question_text_view = view.findViewById(R.id.bottom_sheet_question)
        answer_text_view = view.findViewById(R.id.bottom_sheet_answer)
        //val intent = getIntentOld("QUESTION_ID")
        //question_id = intent.getStringExtra(QuestionActivity().QUESTION_ID)
        //requestGraphql(question_id)
        Log.e("qeustionid", question_id)
        requestGraphql(question_id)
        return view
    }

    private fun requestGraphql(question_id: String) {
        apolloclient.getQuestionQueryCall(question_id).enqueue(
                //For instantiate abstract class in Kotlin you use object: <your class>. Example:
                object : ApolloCall.Callback<QuestionQuery.Data>() {
                    override fun onFailure(e: ApolloException) {
                        Log.e("errormessage", e.message.toString())
                    }

                    override fun onResponse(response: Response<QuestionQuery.Data>) {
                        Log.e("responsemessage", response.data()!!.question().toString())
                        if (response.data()!!.question()!!.answerList() == null) {
                            return
                        }
                        if (response.data()!!.question()!!.contents() == null) {
                            return
                        }
                        else {
                            for (item in response.data()!!.question()!!.answerList()!!.edges()) {
                                //Log.e("logfor",item.contents().toString())

                                answer_text_view.text = item.node()!!.contents()
                                //Log.e("lists",lists.toString())

                            }
                        }
                    }
                }
        )
    }
}


/*
* */