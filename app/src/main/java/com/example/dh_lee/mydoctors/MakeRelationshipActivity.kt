package com.example.dh_lee.mydoctors

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView

class MakeRelationshipActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_relationship)
        val finddoctor = findViewById<MaterialCardView>(R.id.clicktodoctorcode)
        val findhospital = findViewById<MaterialCardView>(R.id.clicktohospital)
        finddoctor.setOnClickListener{
            val intent =Intent(this,QuestionActivity::class.java)
            startActivity(intent)
        }
        findhospital.setOnClickListener{
            val intent =Intent(this,QuestionActivity::class.java)
            startActivity(intent)
        }
    }
}