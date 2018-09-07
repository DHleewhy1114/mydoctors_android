package com.example.dh_lee.mydoctors

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import mutations.MakeRelationshipMutation
import okhttp3.OkHttpClient
import queries.*
import type.CreateRelationshipInput
import java.util.concurrent.TimeUnit


object apolloclient {
    private val BASE_URL_GRAPHQL = "http://10.0.2.2:5000/graphql"
    //   private val BASE_URL_GRAPHQL = "http://220.86.193.189:5000/graphql"
    //   private val BASE_URL_GRAPHQL = "http://http://192.168.10.109:5000/graphql"
    private val apolloClient: ApolloClient
    private val doctorlistQueryClient: DoctorlistQuery
    private val hospitallistQueryClient: HospitallistQuery
    private val hospitalQueryClient:HospitalQuery.Builder
    private val mydoctorsQueryClient:MydoctorsQuery.Builder
    private val doctorByCodeQueryClient:FindDoctorByCodeQuery.Builder
    private val questionByUserQueryClient:QuestionByUserQuery.Builder
    init {
        val okHttpClient = OkHttpClient.Builder()
                .pingInterval(30, TimeUnit.SECONDS)
                .build()

        apolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL_GRAPHQL).build()

        doctorlistQueryClient = DoctorlistQuery.builder().build()
        hospitallistQueryClient = HospitallistQuery.builder().build()
        hospitalQueryClient = HospitalQuery.builder()
        mydoctorsQueryClient = MydoctorsQuery.builder()
        doctorByCodeQueryClient = FindDoctorByCodeQuery.builder()
        questionByUserQueryClient = QuestionByUserQuery.builder()
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
    fun getMydoctorsQueryClient(id:String): MydoctorsQuery {
        return mydoctorsQueryClient.id(id).build()
    }
    fun getMydoctorsQueryCall(id:String): ApolloCall<MydoctorsQuery.Data> {
        return apolloClient.query(mydoctorsQueryClient.id(id).build())
    }
    fun getHospitallistQueryClient(): HospitallistQuery {
        return hospitallistQueryClient
    }
    fun getHospitallistQueryCall(): ApolloCall<HospitallistQuery.Data> {
        return apolloClient.query(hospitallistQueryClient)
    }

    fun getHospitalQueryClient(id:String): HospitalQuery {
        return hospitalQueryClient.id(id).build()
    }
    fun getHospitalQueryCall(id:String): ApolloCall<HospitalQuery.Data> {
        return apolloClient.query(hospitalQueryClient.id(id).build())
    }
    fun getDoctorByCodeQueryClient(doctorCode:Int):FindDoctorByCodeQuery{
        return doctorByCodeQueryClient.doctorCode(doctorCode).build()
    }
    fun getDoctorByCodeQueryCall(doctorCode: Int):ApolloCall<FindDoctorByCodeQuery.Data>{
        return apolloClient.query(doctorByCodeQueryClient.doctorCode(doctorCode).build())
    }

    fun makeRelationshipMutationClient(uid:String,did:String): MakeRelationshipMutation {
        val builder = MakeRelationshipMutation.builder()
        val input =CreateRelationshipInput.builder().uid(uid).did(did).build()
        builder.input(input)
        return builder.build()
    }
    fun makeRelationshipMutationCall(mutationBuilded: MakeRelationshipMutation): ApolloCall<MakeRelationshipMutation.Data> {
        return apolloClient.mutate(mutationBuilded)
    }
    fun makeQuestionMutationClient(uid:String,did:String):QuestionByUserQuery{
        return questionByUserQueryClient.uid(uid).did(did).build()
    }
    fun makeQuestionMutationCall(uid:String,did: String):ApolloCall<QuestionByUserQuery.Data>{
        return apolloClient.query(questionByUserQueryClient.uid(uid).did(did).build())
    }


}

/*fun addlists(recycle: RecyclerView){
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
                    // 데이터가 변했음을 swapadapter에 전달
                    /* runOnUiThread({
                         test!!.text = response.data()!!.doctorlist()!!.toString()

                     })*/
                }
            }

    )
}*/


