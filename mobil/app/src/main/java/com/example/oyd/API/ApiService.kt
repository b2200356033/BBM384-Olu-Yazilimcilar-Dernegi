package com.example.oyd.API

import android.telecom.CallScreeningService.CallResponse
import com.example.oyd.Models.Course
import com.example.oyd.Models.FileDB
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


interface ApiService {




    @POST("/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>



    //COURSE RELATED CALLS
    @GET("/course/{id}")
    suspend fun apiGetCourseFromServer(@Path("id") id: Long): Response<Course>
    @GET("/student/{studentId}/courses")
    suspend fun apiGetStudentCoursesFromServer(@Path("studentId") studentId: Long): Response<ArrayList<Course>>
    @POST("/student/{studentId}/courses/{courseId}")
    suspend fun apiAddCourseToStudent(@Path("studentId") studentId: Long, @Path("courseId") courseId: Long): Response<Void>
    @DELETE("/student/{studentId}/courses/{courseId}")
    suspend fun apiDeleteCourseFromStudent(@Path("studentId") studentId: Long, @Path("courseId") courseId: Long): Response<Void>
    @POST("/course")
    suspend fun apisendCourseToServer(@Body course: Course): Response<Course>

    @PUT("/course/setInstructor")
    suspend fun apisetInstructorToCourse(@Body user: Course): Response<Course>
    @GET("/course/")
    suspend fun apiGetAllCourseFromServer(): Response<ArrayList<Course>>
    @PUT("/course/{id}")
    suspend fun apiUpdateCourse(@Body id: Long): Response<Course>
    @DELETE("/course/{id}")
    suspend fun apiDeleteCourseFromServer(@Body id: Long): Response<Course>




    //ADD NEW USER
    @POST("/departmentmanager")
    suspend fun apisendDepartmentManagerToServer(@Body user: DepartmentManager): Response<DepartmentManager>

    @POST("/admin")
    suspend fun apisendAdminToServer(@Body user: Admin): Response<Admin>

    @POST("/student")
    suspend fun apisendStudentToServer(@Body user: Student): Response<Student>

    @POST("/instructor")
    suspend fun apisendInstructorToServer(@Body user: Instructor): Response<Instructor>
    //ADD NEW USER




    @POST("/instructor/all")
    suspend fun apiGetAllInstructorFromServer(): Response<List<Instructor>>
    @GET("/instructor/{id}")
    suspend fun apiGetInstructorFromServer(@Body id: Long): Response<Instructor>
    @PUT("/instructor/{id}")
    suspend fun apiUpdateInstructor(@Body id: Long): Response<Instructor>
    @DELETE("/instructor/{id}")
    suspend fun apiDeleteInstructorFromServer(@Body id: Long): Response<Instructor>

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
    //DELETE USER



    //SIGN UP
    @POST("/signupAdmin")
    fun signupAdmin(@Body admin: Admin): Call<ResponseBody>

    @POST("/signupStudent")
    fun signupStudent(@Body student: Student): Call<ResponseBody>

    @POST("/signupInstructor")
    fun signupInstructor(@Body instructor: Instructor): Call<ResponseBody>

    @POST("/signupDepartmentManager")
    fun signupDepartmentManager(@Body departmentManager: DepartmentManager): Call<ResponseBody>
    //SIGN UP



    //BAN USER
    @PUT("/student/ban/email/{email}")
    suspend fun apiBanStudentByEmail(@Path("email") email: String): Response<Student>

    @PUT("/student/ban/fullname/{name}/{surname}")
    suspend fun apiBanStudentByName(@Path("name") name: String, @Path("surname") surname: String): Response<Student>
    //BAN USER



    //MANAGE EMAIL ADDRESS
    @PUT("/student/manage/email/{email}")
    suspend fun apiManageEmailStudent(@Path("email") oldEmail: String, @Body newEmail: String): Response<Student>

    @PUT("/admin/manage/email/{email}")
    suspend fun apiManageEmailAdmin(@Path("email") oldEmail: String, @Body newEmail: String): Response<Admin>

    @PUT("/departmentmanager/manage/email/{email}")
    suspend fun apiManageEmailDepartmentManager(@Path("email") oldEmail: String, @Body newEmail: String): Response<DepartmentManager>

    @PUT("/instructor/manage/email/{email}")
    suspend fun apiManageEmailInstructor(@Path("email") oldEmail: String, @Body newEmail: String): Response<Instructor>
    //MANAGE EMAIL ADDRESS



    @POST("/departmentmanager/addfile")
    suspend fun apiAddFileToDepartmentManager(@Body file: FileDB): Response<Void>

    @POST("departmentmanager/addfile/{email}")
    suspend fun apiAddFileToDepartmentManagerByEmail(@Path("email") email: String, @Body file: FileDB): Response<Void>

}


