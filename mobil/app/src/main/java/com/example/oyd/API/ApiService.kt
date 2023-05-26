package com.example.oyd.API

import android.telecom.CallScreeningService.CallResponse
import com.example.oyd.Models.Course
import com.example.oyd.Models.Semester

import com.example.oyd.Users.Admin
import com.example.oyd.Users.Student
import com.example.oyd.Users.Instructor
import com.example.oyd.Users.DepartmentManager
import okhttp3.ResponseBody
import org.w3c.dom.Entity
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE

import retrofit2.http.Headers

import retrofit2.http.Path

import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface ApiService {




    @POST("/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>




    @GET("/course/{id}")
    suspend fun apiGetCourseFromServer(@Body id: Int): Response<Course>
    @POST("/course")
    suspend fun apisendCourseToServer(@Body course: Course): Response<Course>

    @PUT("/course/setInstructor")
    suspend fun apisetInstructorToCourse(@Body user: Course): Response<Course>
    @GET("/course/")
    suspend fun apiGetAllCourseFromServer(): Response<List<Course>>
    @PUT("/course/{id}")
    suspend fun apiUpdateCourse(@Body id: Int): Response<Course>
    @DELETE("/course/{id}")
    suspend fun apiDeleteCourseFromServer(@Body id: Int): Response<Course>





    @POST("/admin")
    suspend fun apisendAdminToServer(@Body user: Admin): Response<Admin>

    @POST("/student")
    suspend fun apisendStudentToServer(@Body user: Student): Response<Student>




    @GET("/instructor/")
    suspend fun apisendInstructorToServer(@Body user: Instructor): Response<Instructor>
    @POST("/instructor/all")
    suspend fun apiGetAllInstructorFromServer(): Response<List<Instructor>>
    @GET("/instructor/{id}")
    suspend fun apiGetInstructorFromServer(@Body id: Int): Response<Instructor>
    @PUT("/instructor/{id}")
    suspend fun apiUpdateInstructor(@Body id: Int): Response<Instructor>
    @DELETE("/instructor/{id}")
    suspend fun apiDeleteInstructorFromServer(@Body id: Int): Response<Instructor>

    @POST("/departmentmanager")

    suspend fun apisendDepartmentManagerToServer(@Body user: DepartmentManager): Response<DepartmentManager>
    @POST("/semester")
    suspend fun apisendSemesterToServer(@Body user: Semester): Response<Semester>

    //DELETE USER
    @DELETE("/student/email/{email}")
    suspend fun apiDeleteStudentByEmail(@Path("email") email: String): Response<Student>

    @DELETE("/admin/email/{email}")
    suspend fun apiDeleteAdminByEmail(@Path("email") email: String): Response<Admin>

    @DELETE("/departmentmanager/email/{email}")
    suspend fun apiDeleteDepartmentManagerByEmail(@Path("email") email: String): Response<DepartmentManager>

    @DELETE("/instructor/email/{email}")
    suspend fun apiDeleteInstructorByEmail(@Path("email") email: String): Response<Instructor>

    @DELETE("/student/fullname/{name}/{surname}")
    suspend fun apiDeleteStudentByName(@Path("name") name: String, @Path("surname") surname: String): Response<Student>

    @DELETE("/admin/fullname/{name}/{surname}")
    suspend fun apiDeleteAdminByName(@Path("name") name: String, @Path("surname") surname: String): Response<Admin>

    @DELETE("/departmentmanager/fullname/{name}/{surname}")
    suspend fun apiDeleteDepartmentManagerByName(@Path("name") name: String, @Path("surname") surname: String): Response<DepartmentManager>


    @DELETE("/instructor/fullname/{name}/{surname}")
    suspend fun apiDeleteInstructorByName(@Path("name") name: String, @Path("surname") surname: String): Response<Instructor>

}



