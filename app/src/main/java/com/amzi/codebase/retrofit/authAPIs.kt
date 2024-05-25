package com.amzi.codebase.retrofit

import com.amzi.codebase.retrofit.response.loginResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface authAPIs {

    @FormUrlEncoded
    @POST("/starter_operator/login")
    suspend fun login(
        @Field("mobile_no") mobile_no:String,
        @Field("password") password:String
    ): loginResponse



}