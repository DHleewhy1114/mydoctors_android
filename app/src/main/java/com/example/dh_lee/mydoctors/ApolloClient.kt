package com.example.dh_lee.mydoctors

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import mutations.MakeQuestionMutation
import mutations.MakeRelationshipMutation
import okhttp3.OkHttpClient
import queries.*
import type.CreateQuestionInput
import type.CreateRelationshipInput
import java.util.concurrent.TimeUnit


object apolloclient {
    private val BASE_URL_GRAPHQL = "http://10.0.2.2:5000/graphql" // 서버 주소
    //   private val BASE_URL_GRAPHQL = "http://220.86.193.189:5000/graphql"
    //   private val BASE_URL_GRAPHQL = "http://192.168.10.109:5000/graphql"
    private val apolloClient: ApolloClient
    private val doctorlistQueryClient: DoctorlistQuery
    private val hospitallistQueryClient: HospitallistQuery
    private val hospitalQueryClient: HospitalQuery.Builder
    private val mydoctorsQueryClient: MydoctorsQuery.Builder
    private val doctorByCodeQueryClient: FindDoctorByCodeQuery.Builder
    private val getquestionByUserQueryClient: QuestionByUserQuery.Builder
    private val getquestionQueryClient:QuestionQuery.Builder
    //private val func:Unit?=null
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
        getquestionByUserQueryClient = QuestionByUserQuery.builder()
        getquestionQueryClient = QuestionQuery.builder()

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

    fun getMydoctorsQueryClient(id: String): MydoctorsQuery {
        return mydoctorsQueryClient.id(id).build()
    }

    fun getMydoctorsQueryCall(id: String): ApolloCall<MydoctorsQuery.Data> {
        return apolloClient.query(mydoctorsQueryClient.id(id).build())
    }

    fun getHospitallistQueryClient(): HospitallistQuery {
        return hospitallistQueryClient
    }

    fun getHospitallistQueryCall(): ApolloCall<HospitallistQuery.Data> {
        return apolloClient.query(hospitallistQueryClient)
    }

    fun getHospitalQueryClient(id: String): HospitalQuery {
        return hospitalQueryClient.id(id).build()
    }

    fun getHospitalQueryCall(id: String): ApolloCall<HospitalQuery.Data> {
        return apolloClient.query(hospitalQueryClient.id(id).build())
    }

    fun getDoctorByCodeQueryClient(doctorCode: Int): FindDoctorByCodeQuery {
        return doctorByCodeQueryClient.doctorCode(doctorCode).build()
    }

    fun getDoctorByCodeQueryCall(doctorCode: Int): ApolloCall<FindDoctorByCodeQuery.Data> {
        return apolloClient.query(doctorByCodeQueryClient.doctorCode(doctorCode).build())
    }

    fun makeRelationshipMutationClient(uid: String, did: String): MakeRelationshipMutation {
        val builder = MakeRelationshipMutation.builder()
        val input = CreateRelationshipInput.builder().uid(uid).did(did).build()
        builder.input(input)
        return builder.build()
    }

    fun makeRelationshipMutationCall(mutationBuilded: MakeRelationshipMutation): ApolloCall<MakeRelationshipMutation.Data> {
        return apolloClient.mutate(mutationBuilded)
    }

    fun getQuestionByUserQueryClient(uid: String, did: String): QuestionByUserQuery {
        return getquestionByUserQueryClient.uid(uid).did(did).build()
    }

    fun getQuestionByUserQueryCall(uid: String, did: String): ApolloCall<QuestionByUserQuery.Data> {
        return apolloClient.query(getquestionByUserQueryClient.uid(uid).did(did).build())
    }
    fun getQuestionQueryClient(question_id:String):QuestionQuery{
        return getquestionQueryClient.id(question_id).build()
    }
    fun getQuestionQueryCall(question_id: String):ApolloCall<QuestionQuery.Data>{
       return apolloClient.query(getQuestionQueryClient(question_id))
    }
    fun makeQuestionMutationClient(uid: String,did: String,contents:String): MakeQuestionMutation {
        val builder = MakeQuestionMutation.builder()
        val input = CreateQuestionInput.builder().uid(uid).did(did).contents(contents).build()
        builder.input(input)
        return builder.build()
    }

    fun makeQuestionMutationCall(mutationBuilded: MakeQuestionMutation): ApolloCall<MakeQuestionMutation.Data> {
        return apolloClient.mutate(mutationBuilded)
    }

}



