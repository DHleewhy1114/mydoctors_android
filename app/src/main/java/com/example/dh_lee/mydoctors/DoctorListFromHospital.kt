package com.example.dh_lee.mydoctors

import android.content.Context
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
import queries.DoctorlistQuery
import com.example.dh_lee.mydoctors.FindByHospitalActivity
import mutations.MakeRelationshipMutation
import queries.HospitalQuery


class DoctorListFromHospital : AppCompatActivity() {
    val lists:ArrayList<DoctorData> = ArrayList()
    private val uid = "VXNlcjox"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_list_from_hospital)
        val doctor_by_hospital_recycle = findViewById<RecyclerView>(R.id.doctorfromhospitalrecycler)
        val intent = getIntent()
        val hospital_id = intent.getStringExtra(FindByHospitalActivity().HOSPITAL_ID)
        Log.e("hospital_id",hospital_id)
        requestGraphql(doctor_by_hospital_recycle,hospital_id)
        makeRecyclerview(doctor_by_hospital_recycle)
        //lists 업데이트이후 재조정
        //recycle.swapAdapter(RelationshipAdapter(lists,this,{ item:ItemData -> itemClicked(item) }),true)
    }
    fun toRelationshipAct(){
        val intent = Intent(this,RelationshipActivity::class.java)
        startActivity(intent)
    }
    private fun itemClicked(uid:String,item : DoctorData) {
        apolloclient.makeRelationshipMutationCall(apolloclient.makeRelationshipMutationClient(uid,item.doctor_id)).enqueue(
                object:ApolloCall.Callback<MakeRelationshipMutation.Data>(){
                    override fun onFailure(e: ApolloException) {
                        Log.e("makerelationshipFail",e.message.toString())
                    }
                    override fun onResponse(response: Response<MakeRelationshipMutation.Data>) {
                        Log.e("makerelationshipstart",response.data().toString())
                        //Toast.makeText(this,"추가되었습니다",Toast.LENGTH_SHORT)
                        //toRelationshipAct()
                        toRelationshipAct()
                    }
                }
        )
    }
    private fun requestGraphql(recycle:RecyclerView,id:String){
        apolloclient.getHospitalQueryCall(id).enqueue(
                //For instantiate abstract class in Kotlin you use object: <your class>. Example:
                object: ApolloCall.Callback<HospitalQuery.Data>(){
                    override fun onFailure(e: ApolloException) {
                        Log.e("errormessage",e.message.toString())
                    }

                    override fun onResponse(response: Response<HospitalQuery.Data>) {

                        Log.e("responsemessage", response.data()!!.hospital()!!.doctorList()?.edges().toString())
                        //response.data()!!.doctorlist()!!.edges().get(0).node()!!.doctorName()
                        for(item in response.data()!!.hospital()!!.doctorList()!!.edges()){
                            Log.e("logfor",item.node()!!.doctorName().toString())

                            lists.add(DoctorData(item.node()!!.doctorName().toString(),item.node()!!.doctorCode().toString(),item.node()!!.id()))
                            Log.e("lists",lists.toString())


                        }
                        runOnUiThread {
                            recycle.swapAdapter(RelationshipAdapter(lists, this@DoctorListFromHospital, { item: DoctorData -> itemClicked(uid, item) }), true)
                        }//api로 ui를 건드리지말고 runOnUiThread사용
                        // 데이터가 변했음을 swapadapter에 전달
                        /* runOnUiThread({
                             test!!.text = response.data()!!.doctorlist()!!.toString()

                         })*/
                    }
                }

        )
    }
    private fun makeRecyclerview(recycle:RecyclerView){
        recycle.layoutManager = LinearLayoutManager(this)
        recycle.layoutManager = GridLayoutManager(this, 2)
        recycle.adapter = RelationshipAdapter(lists, this,{ item:DoctorData -> itemClicked(uid,item) })
    }
}
