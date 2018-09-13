package com.example.dh_lee.mydoctors

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.nhn.android.maps.NMapCompassManager
import com.nhn.android.maps.NMapLocationManager
import com.nhn.android.mapviewer.overlay.NMapOverlayManager
import com.nhn.android.mapviewer.overlay.NMapResourceProvider
import okhttp3.OkHttpClient
import queries.DoctorlistQuery
import java.util.concurrent.TimeUnit
import com.example.dh_lee.mydoctors.apolloclient
import queries.HospitallistQuery

//https://developers.naver.com/docs/map/android/
class FindByHospitalActivity : AppCompatActivity() {
    val lists:ArrayList<HospitalData> = ArrayList()
    private lateinit var hospital_recycle:RecyclerView
    var HOSPITAL_ID = "HOSPITAL_ID"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_by_hospital)
        var hospital_recycle=findViewById<RecyclerView>(R.id.doctor_horizon)

        requestGraphql(hospital_recycle)
        makeRecyclerview(hospital_recycle)

    }
    private fun toDoctorFromHospital(hospital : HospitalData) {
        val intent = Intent(this,DoctorListFromHospital::class.java)
        val hospitalId=hospital.hospital_id
        intent.putExtra(HOSPITAL_ID, hospitalId);
        startActivity(intent)
    }

    private fun requestGraphql(recycle:RecyclerView){
        apolloclient.getHospitallistQueryCall().enqueue(
                object: ApolloCall.Callback<HospitallistQuery.Data>(){
                    override fun onFailure(e: ApolloException) {
                        Log.e("errormessage",e.message.toString())
                    }

                    override fun onResponse(response: Response<HospitallistQuery.Data>) {
                        Log.e("responsemessage", response.data()!!.hospitallist()!!.edges().toString())
                        for(item in response.data()!!.hospitallist()!!.edges()){
                            Log.e("logfor",item.node()!!.toString())
                            lists.add(HospitalData(item.node()!!.hospitalName().toString(),"",item.node()!!.id()))
                            Log.e("lists",lists.toString())


                        }
                        runOnUiThread {
                            recycle.swapAdapter(HospitalAdapter(lists, this@FindByHospitalActivity, { hospital: HospitalData -> toDoctorFromHospital(hospital) }), true)
                        }//api로 ui를 건드리지말고 runOnUiThread사용
                        // 데이터가 변했음을 swapadapter에 전달
                    }
                }

        )
    }
    private fun makeRecyclerview(recycle:RecyclerView?){
        recycle?.layoutManager = LinearLayoutManager(this)
        recycle?.layoutManager = GridLayoutManager(this,2)
        recycle?.adapter = HospitalAdapter(lists, this,{ hospital:HospitalData -> toDoctorFromHospital(hospital) })
    }
}
