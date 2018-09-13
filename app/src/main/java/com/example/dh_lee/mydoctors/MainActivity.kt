package com.example.dh_lee.mydoctors

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.apollographql.apollo.ApolloClient




class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val relationshipintent = Intent(this,RelationshipActivity::class.java)//로그인 과정 우선 스킵 추후에 로그인 기능 넣을 때 user key 받아서 넘길것
        startActivity(relationshipintent) // 내가 등록한 의사 리스트로
    }


}
