package com.example.dh_lee.mydoctors

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.apollographql.apollo.ApolloClient




class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val loginintent = Intent(this, LoginActivity::class.java)
        //val loginintent = Intent(this, QuestionActivity::class.java)
        //val loginintent = Intent(this,MapTest::class.java)
        val relationshipintent = Intent(this,RelationshipActivity::class.java)
        //val loginintent = Intent(this, TakePictureActivity::class.java)
        //val loginintent = Intent(this, MakeRelationshipActivity::class.java)
        startActivity(relationshipintent)
        /*apolloClient().query(
                doctor.builder()
                        .limit(10)
                        .type(FeedType.HOT)
                        .build()
        )*/
    }


}
