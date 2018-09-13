package com.example.dh_lee.mydoctors

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import mutations.MakeQuestionMutation
import queries.MydoctorsQuery
import queries.QuestionByUserQuery

class QuestionActivity : AppCompatActivity() {
    var lists:ArrayList<QuestionList> = ArrayList()
    val QUESTION_ID = "QUESTION_ID"
    private lateinit var question_text:TextInputEditText
    private lateinit var question_send_button:MaterialButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        val recycle: RecyclerView = findViewById(R.id.questionlist)
        recycle.layoutManager = LinearLayoutManager(this)
        recycle.layoutManager = GridLayoutManager(this, 1)
        recycle.adapter = QuestionAdapter(lists, this,{ item:QuestionList -> itemClicked(item) })
        val doctor_id = getIntent().getStringExtra(RelationshipActivity().DOCTOR_ID)
        requestGraphql(recycle,"VXNlcjox",doctor_id)
        question_text=findViewById(R.id.question_textfield)
        question_send_button=findViewById(R.id.question_send_button)
        question_send_button.setOnClickListener {
            sendQuestion(recycle,"VXNlcjox",doctor_id)
        }
    }
    //android carousel recyclerview
    private fun itemClicked(item : QuestionList) {
        val intent=Intent(this,AnswerActivity::class.java)
       intent.putExtra(QUESTION_ID,item.question_id)
        val addBottomDialogFragment = AnswerActivity()
        addBottomDialogFragment.question_id=item.question_id
        addBottomDialogFragment.show(supportFragmentManager,
                "add_answer_fragment")


    }
    private fun sendQuestion(recycle: RecyclerView,uid: String,did: String){
        val question_text_contents:String=question_text.text.toString()
        Log.e("text_to_send",question_text_contents)
        //requestGraphql(recycle,uid,did)
        lists = ArrayList()
        sendGraphql(recycle,uid,did,question_text_contents)


    }
    private fun sendGraphql(recycle: RecyclerView,uid:String,did:String,contents:String) {
        Log.e("contents in sendGraphql",contents)
        apolloclient.makeQuestionMutationCall(apolloclient.makeQuestionMutationClient(uid,did,contents)).enqueue(
                object:ApolloCall.Callback<MakeQuestionMutation.Data>(){
                    override fun onFailure(e: ApolloException) {
                        Log.e("makerelationshipFail",e.message.toString())
                    }
                    override fun onResponse(response: Response<MakeQuestionMutation.Data>) {
                        Log.e("makerelationshipstart",response.data().toString())
                        requestGraphql(recycle,uid,did)
                    }
                })
    }
    private fun requestGraphql(recycle:RecyclerView,uid:String,did:String){
        apolloclient.getQuestionByUserQueryCall(uid, did).enqueue(
                //For instantiate abstract class in Kotlin you use object: <your class>. Example:
                object: ApolloCall.Callback<QuestionByUserQuery.Data>(){
                    override fun onFailure(e: ApolloException) {
                        Log.e("errormessage",e.message.toString())
                    }

                    override fun onResponse(response: Response<QuestionByUserQuery.Data>) {

                        Log.e("responsemessage", response.data()!!.questionByUser().toString())
                        //response.data()!!.doctorlist()!!.edges().get(0).node()!!.doctorName()
                        for(item in response.data()!!.questionByUser()!!.iterator()){
                            //Log.e("logfor",item.contents().toString())
                            lists.add(QuestionList(item.contents().toString(),item.id()))
                            //Log.e("lists",lists.toString())


                        }

                        runOnUiThread {
                            recycle.swapAdapter(QuestionAdapter(lists, this@QuestionActivity, { item: QuestionList -> itemClicked(item) }), true)
                        }

                    }
                }

        )
    }
}
//material modalsheet로 답변 표시할것
//https://material.io/design/components/backdrop.html#behavior
//https://material.io/develop/android/components/bottom-sheet-dialog-fragment/
//https://medium.com/@kosta.palash/using-bottomsheetdialogfragment-with-material-design-guideline-f9814c39b9fc