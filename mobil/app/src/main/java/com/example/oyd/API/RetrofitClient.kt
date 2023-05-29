package com.example.oyd.API


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    //spring boot server url will come here


    private const val BASE_URL = "http://192.168.1.2:8080"
    //private const val BASE_URL = "http://192.168.1.36:8080"
    //private const val BASE_URL = "http://192.168.1.153:8080"
    //private const val BASE_URL = "http://10.193.23.6:8080"



    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
}
