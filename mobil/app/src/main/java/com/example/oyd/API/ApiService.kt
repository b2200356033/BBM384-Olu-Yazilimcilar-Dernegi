package com.example.oyd.API

import com.example.oyd.Models.Course
import com.example.oyd.Models.Semester
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

    @POST("/semester")
    suspend fun apisendSemesterToServer(@Body semester : Semester) : Response<Semester>
}



