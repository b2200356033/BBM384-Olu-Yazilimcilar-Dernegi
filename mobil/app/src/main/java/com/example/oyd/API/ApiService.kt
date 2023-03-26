package com.example.oyd.API

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/auth/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<Void>
}



