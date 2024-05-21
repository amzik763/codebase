package com.amzi.codebase.retrofit

import android.content.Context
import com.amzi.codebase.utility.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class remoteDataSource {

    fun <Api> buildApi(
        api: Class<Api>,
        context: Context,
        token: String? = null
    ): Api {
//        TokenAuthenticator(context, buildTokenApi())
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getRetrofitClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }

    private fun getRetrofitClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

}