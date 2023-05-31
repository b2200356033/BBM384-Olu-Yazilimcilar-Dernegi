package com.example.oyd.API

import com.example.oyd.Models.Course
import com.example.oyd.Models.FileDB
import com.example.oyd.Models.Semester
import com.example.oyd.Models.Survey
import com.example.oyd.Models.SurveyFinder

import com.example.oyd.Users.Admin
import com.example.oyd.Users.Student
import com.example.oyd.Users.Instructor
import com.example.oyd.Users.DepartmentManager
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Path
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT


interface ApiService {




    @POST("/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>

    //Survey RELATED CALLS
    @GET("/surveys/{courseId}/survey")
    suspend fun apiGetSurveyWithCourseID(@Path("courseId") id: Long): Response<ArrayList<String>>
    @POST("/surveys/{studentId}/{courseId}/evaluation")
    suspend fun apiSendEvaluationOfSurveyWithStudentAndCourseID(@Path("studentId") studentId: Long,
                                                                @Path("courseId") courseId: Long,
                                                                @Body list: ArrayList<Float> ):Response<Void>

    //COURSE RELATED CALLS
    @GET("/course/{id}")
    suspend fun apiGetCourseFromServer(@Path("id") id: Long): Response<Course>
    @GET("/student/{studentId}/courses")
    suspend fun apiGetStudentCoursesFromServer(@Path("studentId") studentId: Long): Response<ArrayList<Course>>
    @POST("/student/{studentId}/courses/{courseId}")
    suspend fun apiAddCourseToStudent(@Path("studentId") studentId: Long, @Path("courseId") courseId: Long): Response<Void>
    @DELETE("/student/{studentId}/courses/{courseId}")
    suspend fun apiDeleteCourseFromStudent(@Path("studentId") studentId: Long, @Path("courseId") courseId: Long): Response<Void>
    @FormUrlEncoded
    @POST("/course")
    suspend fun apisendCourseToServer(@Field("coursename") coursename: String,
                                      @Field("credit") credit: Int,
                                      @Field("department") department: String,
                                      @Field("type") type: String): Response<Course>

    @PUT("/course/setInstructor")
    suspend fun apisetInstructorToCourse(@Body user: Course): Response<Course>
    @GET("/course/")
    suspend fun apiGetAllCourseFromServer(): Response<ArrayList<Course>>
    @GET("/course/withSurvey/{studentId}")
    suspend fun apiGetAllCoursesWithSurveyFromServer(@Path("studentId") studentId: Long): Response<ArrayList<Course>>
    @PUT("/course/{id}")
    suspend fun apiUpdateCourse(@Body id: Long): Response<Course>
    @DELETE("/course/{id}")
    suspend fun apiDeleteCourseFromServer(@Body id: Long): Response<Course>



    //ADD NEW USER
    @POST("/departmentmanager")
    fun apisendDepartmentManagerToServer(@Body user: SignupRequest): Call<DepartmentManager>

    @POST("/admin")
    fun apisendAdminToServer(@Body user: SignupRequest): Call<Admin>

    @POST("/student")
    fun apisendStudentToServer(@Body user: SignupRequest): Call<Student>

    @POST("/instructor")
    fun apisendInstructorToServer(@Body user: SignupRequest): Call<Instructor>
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



    //GET USER
    @GET("/student/email/{email}")
    suspend fun apiGetStudent(@Path("email") email: String): Response<Student>

    @GET("/admin/email/{email}")
    suspend fun apiGetAdmin(@Path("email") email: String): Response<Admin>

    @GET("/departmentmanager/email/{email}")
    suspend fun apiGetDepartmentMenager(@Path("email") email: String): Response<DepartmentManager>

    @GET("/instructor/email/{email}")
    suspend fun apiGetInstructor(@Path("email") email: String): Response<Instructor>
    //GET USER



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
    fun signupAdmin(@Body admin: SignupRequest): Call<Admin>

    @POST("/signupStudent")
    fun signupStudent(@Body student: SignupRequest): Call<Student>

    @POST("/signupInstructor")
    fun signupInstructor(@Body instructor: SignupRequest): Call<Instructor>

    @POST("/signupDepartmentManager")
    fun signupDepartmentManager(@Body departmentManager: SignupRequest): Call<DepartmentManager>

    //SIGN UP



    //BAN USER
    @PUT("/student/ban/email/{email}")
    suspend fun apiBanStudentByEmail(@Path("email") email: String): Response<Student>

    @PUT("/student/ban/fullname/{name}/{surname}")
    suspend fun apiBanStudentByName(@Path("name") name: String, @Path("surname") surname: String): Response<Student>
    //BAN USER


    //Instructor Related
    @GET("/instructor/{instructorId}/courses")
    suspend fun apiGetInstructorCoursesFromServer(@Path("instructorId") instructorId: Long): Response<ArrayList<Course>>

    @POST("/surveys")
    suspend fun apiSendSurveyToServer(@Body user: SurveyFinder):Response<Survey>




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



    //MANAGE PASSWORD
    @FormUrlEncoded
    @PUT("/student/manage/password/{email}")
    suspend fun apiManagePasswordStudent(@Path("email") email: String, @Field("old") oldPassword: String, @Field("new") newPassword: String): Response<Student>

    @FormUrlEncoded
    @PUT("/admin/manage/password/{email}")
    suspend fun apiManagePasswordAdmin(@Path("email") email: String, @Field("old") oldPassword: String, @Field("new") newPassword: String): Response<Admin>

    @FormUrlEncoded
    @PUT("/departmentmanager/manage/password/{email}")
    suspend fun apiManagePasswordDepartmentManager(@Path("email") email: String, @Field("old") oldPassword: String, @Field("new") newPassword: String): Response<DepartmentManager>

    @FormUrlEncoded
    @PUT("/instructor/manage/password/{email}")
    suspend fun apiManagePasswordInstructor(@Path("email") email: String, @Field("old") oldPassword: String, @Field("new") newPassword: String): Response<Instructor>
    //MANAGE PASSWORD



    @POST("/departmentmanager/addfile")
    suspend fun apiAddFileToDepartmentManager(@Body file: FileDB): Response<Void>

    @POST("departmentmanager/addfile/{email}")
    suspend fun apiAddFileToDepartmentManagerByEmail(@Path("email") email: String, @Body file: FileDB): Response<Void>

    @GET("/departmentmanager/files/{id}")
    suspend fun apiGetDepartmentManagerFilesFromServer(@Path("id") id: Long): Response<ArrayList<FileDB>>

    @DELETE("/departmentmanager/{dpid}/deletefile/{fileid}")
    suspend fun apiDeleteFileFromDepartmentManager(@Path("dpid") dpid: Long, @Path("fileid") fileid: Long): Response<ArrayList<FileDB>>

    @POST("/departmentmanager/assign/{iid}/{cid}")
    suspend fun apiassignInstructortoCourse(@Path("iid") iid: Long, @Path("cid") cid: Long): Response<Instructor>
}




