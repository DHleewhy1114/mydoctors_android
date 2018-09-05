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
import android.widget.PopupWindow
import androidx.transition.Slide
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.activity_relationship.*
import kotlinx.android.synthetic.main.relationship_component.*
import okhttp3.OkHttpClient
import queries.DoctorlistQuery
import queries.DoctorlistQuery.Doctorlist
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class RelationshipActivity : AppCompatActivity() {
    //class ItemData(val doctor_name:String,val doctorinfo:String)
    val lists:ArrayList<ItemData> = ArrayList()
    //val list2:ArrayList<DoctorlistQuery.Data>? = ArrayList()
    //var test:TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_relationship)
        val recycle = findViewById<RecyclerView>(R.id.doctorrelationshiprecycler)
        //addtest()
        addlists(recycle)
        callbackapollo(recycle)
        //lists 업데이트이후 재조정
        recycle.swapAdapter(RelationshipAdapter(lists,this,{ item:ItemData -> itemClicked(item) }),true)
        val fab = findViewById<FloatingActionButton>(R.id.main_float) as FloatingActionButton
        fab.setOnClickListener {
            //popup 함수
            mainclick()
        }

    }
    private fun mainclick(){
        val popupView = layoutInflater.inflate(R.layout.activity_make_relationship, null)
        val mPopupWindow = PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        //popupView 에서 (LinearLayout 을 사용) 레이아웃이 둘러싸고 있는 컨텐츠의 크기 만큼 팝업 크기를 지정
        mPopupWindow.setFocusable(true) // 외부 영역 선택히 PopUp 종료
        mPopupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0)
        val finddoctor = popupView.findViewById(R.id.clicktodoctorcode) as MaterialCardView
        val findhospital = popupView.findViewById(R.id.clicktohospital) as MaterialCardView
        finddoctor.setOnClickListener{
            val intent =Intent(this,FindByCodeActivity::class.java)
            startActivity(intent)
        }
        findhospital.setOnClickListener{
            val intent =Intent(this,FindByHospitalActivity::class.java)
            startActivity(intent)
        }
    }

    private fun startactbyclick(getintent:AppCompatActivity){
        val intent =Intent(this,getintent::class.java)
        startActivity(intent)
    }
    private fun itemClicked(item : ItemData) {
        Toast.makeText(this, "Clicked: ${item.doctor_name}", Toast.LENGTH_LONG).show()
        val intent =Intent(this,QuestionActivity::class.java)
        startActivity(intent)
    }
    private fun addtest(){
        val a = ItemData("ㅆㅆ병원","주소주소주소1")
        val b = ItemData("ㅇㅇ병원","주소주소주소2")
        val c =ItemData("ㅃㅃ병원","주소주소주소3")
        lists.add(a)
        lists.add(b)
        lists.add(c)
        lists.add(a)
        lists.add(b)
        lists.add(c)
    }
    private fun addlists(recycle:RecyclerView){
         apolloclient.getDoctorlistQueryCall().enqueue(
                //For instantiate abstract class in Kotlin you use object: <your class>. Example:
                object:ApolloCall.Callback<DoctorlistQuery.Data>(){
                    override fun onFailure(e: ApolloException) {
                        Log.e("errormessage",e.message.toString())
                    }

                    override fun onResponse(response: Response<DoctorlistQuery.Data>) {

                        Log.e("responsemessage", response.data()!!.doctorlist()!!.edges().toString())
                        //response.data()!!.doctorlist()!!.edges().get(0).node()!!.doctorName()
                        for(item in response.data()!!.doctorlist()!!.edges()){
                            Log.e("logfor",item.node()!!.doctorName().toString())

                            lists.add(ItemData(item.node()!!.doctorName().toString(),item.node()!!.doctorCode().toString()))
                            Log.e("lists",lists.toString())


                        }
                        runOnUiThread {
                            recycle.swapAdapter(RelationshipAdapter(lists, this@RelationshipActivity, { item: ItemData -> itemClicked(item) }), true)
                        }//api로 ui를 건드리지말고 runOnUiThread사용
                        //또는 데이터가 변했음을 swapadapter에 전달
                      /* runOnUiThread({
                           test!!.text = response.data()!!.doctorlist()!!.toString()

                       })*/
                    }
                }

        )
    }
    private fun callbackapollo(recycle:RecyclerView){
        recycle.layoutManager = LinearLayoutManager(this)
        recycle.layoutManager = GridLayoutManager(this, 2)
        recycle.adapter = RelationshipAdapter(lists, this,{ item:ItemData -> itemClicked(item) })
    }
}

object apolloclient {
    private val BASE_URL_GRAPHQL = "http://10.0.2.2:5000/graphql"
    private val apolloClient: ApolloClient
    private val doctorlistQueryClient: DoctorlistQuery
    init {
        val okHttpClient = OkHttpClient.Builder()
                .pingInterval(30, TimeUnit.SECONDS)
                .build()

        apolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL_GRAPHQL).build()

        doctorlistQueryClient = DoctorlistQuery.builder().build()
    }

    fun getApolloClient(): ApolloClient {
        return apolloClient
    }
    fun getDoctorlistQueryClient(): DoctorlistQuery {
        return doctorlistQueryClient
    }
    fun getDoctorlistQueryCall(): ApolloCall<DoctorlistQuery.Data> {
        return apolloClient.query(doctorlistQueryClient)
    }

}


