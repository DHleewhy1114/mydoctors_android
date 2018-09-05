package com.example.dh_lee.mydoctors

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class QuestionActivity : AppCompatActivity() {
    var lists:ArrayList<QuestionList> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        val recycle: RecyclerView = findViewById(R.id.questionlist)
        addlists()
        recycle.layoutManager = LinearLayoutManager(this)
        recycle.layoutManager = GridLayoutManager(this, 1)
        recycle.adapter = QuestionAdapter(lists, this,{ item:QuestionList -> itemClicked(item) })
        /*val addPhotoBottomDialogFragment = AnswerActivity()
        addPhotoBottomDialogFragment.show(supportFragmentManager,
                "add_photo_dialog_fragment")*/
    }
    //android carousel recyclerview
    private fun itemClicked(item : QuestionList) {
        val addPhotoBottomDialogFragment = AnswerActivity()
        addPhotoBottomDialogFragment.show(supportFragmentManager,
                "add_photo_dialog_fragment")
    }
    fun addlists(){
        val a = QuestionList("배가 아파요")
        val b = QuestionList("머리가가 아파요")
        val c = QuestionList("등이 아파요")
        lists.add(a)
        lists.add(b)
        lists.add(c)
    }
}
//material modalsheet로 답변 표시할것
//https://material.io/design/components/backdrop.html#behavior
//https://material.io/develop/android/components/bottom-sheet-dialog-fragment/
//https://medium.com/@kosta.palash/using-bottomsheetdialogfragment-with-material-design-guideline-f9814c39b9fc