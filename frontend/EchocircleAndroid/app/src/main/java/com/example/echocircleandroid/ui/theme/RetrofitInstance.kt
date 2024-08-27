package com.example.echocircleandroid.ui.theme

import com.example.echocircleandroid.network.createUnsafeOkHttpClient
import com.example.echocircleandroid.ui.theme.screens.service.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
//            .baseUrl("https://10.0.2.2:8080/")      // local
            .baseUrl("https://15.165.154.223:8081")     // ec2
            .client(createUnsafeOkHttpClient()) // 안전하지 않은 OkHttpClient 사용
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}


