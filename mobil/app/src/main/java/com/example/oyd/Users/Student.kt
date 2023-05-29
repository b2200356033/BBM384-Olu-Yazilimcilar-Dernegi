package com.example.oyd.Users

import com.example.oyd.Models.Course
import com.example.oyd.Models.Evaluation
import com.google.gson.annotations.SerializedName

class Student(
    //it can be null
    @SerializedName("id")
    var id: Long?,

    @SerializedName("name")
    var name: String?,

    @SerializedName("surname")
    var surname: String?,

    @SerializedName("email")
    var email: String?,

    @SerializedName("password")
    var password: String?,

    @SerializedName("photo")
    var photo: String?,

    @SerializedName("banned")
    var banned: String?,
    @SerializedName("courses")
    var courses: List<Course>?,
    @SerializedName("evaluations")
    var evaluations: List<Evaluation>?
) {
    override fun toString(): String {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email=" + email + '\'' +
                ", password='" + password + '\'' +
                ", photo='" + photo + '\'' +
                ", banned='" + banned + '\'' +
                '}'
    }
}