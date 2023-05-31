package com.example.oyd.Users

import com.example.oyd.Models.Course
import com.google.gson.annotations.SerializedName

class Instructor(
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

    @SerializedName("courses")
    var courses: ArrayList<Course>?
) {
    override fun toString(): String {
        return "Instructor{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email=" + email + '\'' +
                ", password='" + password + '\'' +
                ", photo='" + photo + '\'' +
                ", courses='" + courses + '\'' +
                '}'
    }
}