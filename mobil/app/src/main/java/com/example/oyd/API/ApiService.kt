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
import retrofit2.http.DELETE
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {




    @POST("/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>

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

    @DELETE("/student/{email}")
    suspend fun apiDeleteStudentByEmail(@Path("email") email: String): Response<Student>

    @DELETE("/admin/{email}")
    suspend fun apiDeleteAdminByEmail(@Path("email") email: String): Response<Admin>

    @DELETE("/departmentmanager/{email}")
    suspend fun apiDeleteDepartmentManagerByEmail(@Path("email") email: String): Response<DepartmentManager>

    @DELETE("/instructor/{email}")
    suspend fun apiDeleteInstructorByEmail(@Path("email") email: String): Response<Instructor>

    @DELETE("/student/{name}/{surname}")
    suspend fun apiDeleteStudentByName(@Path("name") name: String, @Path("surname") surname: String): Response<Student>

    @DELETE("/admin/{name}/{surname}")
    suspend fun apiDeleteAdminByName(@Path("name") name: String, @Path("surname") surname: String): Response<Admin>

    @DELETE("/departmentmanager/{name}/{surname}")
    suspend fun apiDeleteDepartmentManagerByName(@Path("name") name: String, @Path("surname") surname: String): Response<DepartmentManager>

    @DELETE("/instructor/{name}/{surname}")
    suspend fun apiDeleteInstructorByName(@Path("name") name: String, @Path("surname") surname: String): Response<Instructor>
}



