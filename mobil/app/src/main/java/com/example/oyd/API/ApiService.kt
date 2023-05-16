package com.example.oyd.API

import com.example.oyd.Models.Course
import com.example.oyd.Models.Semester

import com.example.oyd.Users.Admin
import com.example.oyd.Users.Student
import com.example.oyd.Users.Instructor
import com.example.oyd.Users.DepartmentManager
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {


    @POST("/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<String>


    @POST("/course")
    suspend fun apisendCourseToServer(@Body course: Course): Response<Course>



    @POST("/admin")
    suspend fun apisendAdminToServer(@Body user: Admin): Response<Admin>


    @POST("/student")
    suspend fun apisendStudentToServer(@Body user: Student): Response<Student>


    @POST("/instructor")
    suspend fun apisendInstructorToServer(@Body user: Instructor): Response<Instructor>


    @POST("/departmentmanager")
    suspend fun apisendDepartmentManagerToServer(@Body user: DepartmentManager): Response<DepartmentManager>

    @POST("/semester")
    suspend fun apisendSemesterToServer(@Body user: Semester): Response<Semester>
}



