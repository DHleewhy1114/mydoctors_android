package com.example.dh_lee.mydoctors

import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import mutations.MakeRelationshipMutation
import org.w3c.dom.Text
import queries.FindDoctorByCodeQuery
import queries.MydoctorsQuery

class FindByCodeActivity : AppCompatActivity() {
    private var lists:DoctorData?=null
    private val uid = "VXNlcjox"
    private lateinit var code_text_input:TextInputEditText
    private lateinit var code_button:MaterialButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_by_code)
        code_text_input=findViewById(R.id.doctor_code_input)
        code_button=findViewById(R.id.doctor_code_button)
        code_button.setOnClickListener {
            sendCode()
        }
    }

    private fun sendCode(){
        requestGraphql(code_text_input.text.toString().toInt())
    }

    private fun makepopUp(doctorName:String,doctorInfo:String,doctorId:String) {
        val popupView = layoutInflater.inflate(R.layout.relationship_component, null)
        val doctorname:TextView? = popupView.findViewById(R.id.doctorname)
        val doctorinfo:TextView? = popupView.findViewById(R.id.doctorinfo)
        val list:DoctorData = DoctorData(doctorName,doctorInfo,doctorId)
        doctorname?.text = list.doctor_name//doctorname이 null로 나옴
        doctorinfo?.text = list.doctor_info
        val mPopupWindow = PopupWindow(popupView, ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        mPopupWindow.setFocusable(true) // 외부 영역 선택히 PopUp 종료
        mPopupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0)
        doctorname!!.setOnClickListener{
            makeRelationship(doctorId)
            //Toast.makeText(this@FindByCodeActivity,"추가되었습니다",Toast.LENGTH_SHORT)
        }
    }
    fun toRelationshipAct(){
        val intent = Intent(this,RelationshipActivity::class.java)
        startActivity(intent)
    }
    fun toast(){

    }
    private fun makeRelationship(did:String){
        apolloclient.makeRelationshipMutationCall(apolloclient.makeRelationshipMutationClient(uid,did)).enqueue(
                object:ApolloCall.Callback<MakeRelationshipMutation.Data>(){
                     override fun onFailure(e: ApolloException) {
                        Log.e("makerelationshipFail",e.message.toString())
                    }
                    override fun onResponse(response: Response<MakeRelationshipMutation.Data>) {
                        Log.e("makerelationshipstart",response.data().toString())
                        //Toast.makeText(this,"추가되었습니다",Toast.LENGTH_SHORT)
                        //toRelationshipAct()
                        toRelationshipAct()
                        toast()
                    }
                }
        )
    }
    private fun requestGraphql(doctorCode:Int){
        apolloclient.getDoctorByCodeQueryCall(doctorCode).enqueue(
                //For instantiate abstract class in Kotlin you use object: <your class>. Example:
                object: ApolloCall.Callback<FindDoctorByCodeQuery.Data>(){
                    override fun onFailure(e: ApolloException) {
                        Log.e("errormessage",e.message.toString())
                    }
                    override fun onResponse(response: Response<FindDoctorByCodeQuery.Data>) {
                        Log.e("responsemessage", response.data()?.findDoctorByCode()?.doctorName().toString())
                        runOnUiThread { makepopUp(response.data()?.findDoctorByCode()?.doctorName().toString(),response.data()?.findDoctorByCode()?.doctorCode().toString(),response.data()?.findDoctorByCode()?.id().toString()) }
                        }

                    }
        )

    }

}
