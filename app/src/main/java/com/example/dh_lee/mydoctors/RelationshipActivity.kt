package com.example.dh_lee.mydoctors

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.zip.Inflater
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.widget.AppCompatImageButton
import androidx.transition.Slide
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Query
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.activity_relationship.*
import kotlinx.android.synthetic.main.relationship_component.*
import okhttp3.OkHttpClient
import queries.DoctorlistQuery
import queries.DoctorlistQuery.Doctorlist
import queries.HospitallistQuery
import queries.MydoctorsQuery
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class RelationshipActivity : AppCompatActivity() {
    //class ItemData(val doctor_name:String,val doctorinfo:String)
    val lists:ArrayList<DoctorData> = ArrayList()
    val DOCTOR_ID:String="DOCTOR_ID"
    lateinit var doctor_recycle :RecyclerView
    lateinit var mypage_button :ImageButton
    private lateinit var finddoctor: MaterialCardView
    private lateinit var findhospital:MaterialCardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_relationship)
        doctor_recycle = findViewById(R.id.doctorrelationshiprecycler)
        mypage_button = findViewById(R.id.to_mypage_button)
        requestGraphql(doctor_recycle,"VXNlcjox")
        makeRecyclerview(doctor_recycle)

        val fab = findViewById<FloatingActionButton>(R.id.main_float) as FloatingActionButton
        fab.setOnClickListener {
            //popup 함수
            popMakeRelation()
        }
        mypage_button.setOnClickListener {
            startActByClick(MyPageActivity())
        }

    }
    private fun popMakeRelation(){
        //의사 등록을 위해 코드로 찾을지 병원으로 찾을 지 선택하는 팝업
        val popupView = layoutInflater.inflate(R.layout.activity_make_relationship, null)
        val mPopupWindow = PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        //popupView 에서 (LinearLayout 을 사용) 레이아웃이 둘러싸고 있는 컨텐츠의 크기 만큼 팝업 크기를 지정
        mPopupWindow.setFocusable(true) // 외부 영역 선택 시 PopUp 종료
        mPopupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0)

        finddoctor = popupView.findViewById(R.id.clicktodoctorcode) as MaterialCardView
        findhospital = popupView.findViewById(R.id.clicktohospital) as MaterialCardView

        finddoctor.setOnClickListener{
           startActByClick(FindByCodeActivity())
        }
        findhospital.setOnClickListener{
            startActByClick(FindByHospitalActivity())
        }
    }

    private fun startActByClick(getintent:AppCompatActivity){
        val intent =Intent(this,getintent::class.java)
        startActivity(intent)
    }
    private fun toQuestionAct(item : DoctorData) {
        val intent =Intent(this,QuestionActivity::class.java)
        intent.putExtra(DOCTOR_ID,item.doctor_id)
        startActivity(intent)
    }
    private fun requestGraphql(recycle:RecyclerView,id:String){
         apolloclient.getMydoctorsQueryCall(id).enqueue(
                object:ApolloCall.Callback<MydoctorsQuery.Data>(){
                    override fun onFailure(e: ApolloException) {
                        Log.e("errormessage",e.message.toString())
                    }
                    override fun onResponse(response: Response<MydoctorsQuery.Data>) {
                        Log.e("responsemessage", response.data()!!.mydoctors().toString())
                        for(item in response.data()!!.mydoctors()!!.iterator()){
                            Log.e("logfor",item.doctorName().toString())
                            lists.add(DoctorData(item.doctorName().toString(),item.doctorCode().toString(),item.id()))
                            Log.e("lists",lists.toString())


                        }
                        runOnUiThread {
                            recycle.swapAdapter(RelationshipAdapter(lists, this@RelationshipActivity, { item: DoctorData -> toQuestionAct(item) }), true)
                            //직접 ui를 건드리지말고 runOnUiThread사용
                            // 데이터가 변했음을 swapadapter에 전달
                        }
                    }
                }

        )
    }
    private fun makeRecyclerview(recycle:RecyclerView){
        recycle.layoutManager = LinearLayoutManager(this)
        recycle.layoutManager = GridLayoutManager(this, 2)
        recycle.adapter = RelationshipAdapter(lists, this,{ item:DoctorData -> toQuestionAct(item) })
    }
}
