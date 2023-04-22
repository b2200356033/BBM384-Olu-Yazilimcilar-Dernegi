package com.example.oyd.API

import com.example.oyd.Models.Course
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @POST("api/auth/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<Void>


    @POST("/course")
    suspend fun apisendCourseToServer(@Body course: Course): Response<Course>
}



