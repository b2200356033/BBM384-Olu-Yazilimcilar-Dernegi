package com.example.oyd.API

import com.example.oyd.Models.Course
import com.example.oyd.Models.FileDB
import com.example.oyd.Models.Semester

import com.example.oyd.Users.Admin
import com.example.oyd.Users.Student
import com.example.oyd.Users.Instructor
import com.example.oyd.Users.DepartmentManager
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
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
    @GET("/departmentmanager/{email}/files")
    suspend fun apiGetDepartmentManagerFilesFromServer(@Path("email") email: String): Response<List<FileDB>>

    @GET("/departmentmanager/{email}")
    suspend fun apiGetDepartmentManagerFromServer(@Path("email") email: String): Response<DepartmentManager>
    @PUT("/departmentmanager/{email}/addFile")
    suspend fun apiAddFileToDepartmentManager(@Path("email") email: String, @Body file: FileDB): Response<String>
    @POST("/semester")
    suspend fun apisendSemesterToServer(@Body user: Semester): Response<Semester>


}



