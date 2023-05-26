package com.example.oyd.Models

import com.example.oyd.Users.Instructor
import com.example.oyd.Users.Student
import com.google.gson.annotations.SerializedName

class Course(
    @SerializedName("id")
    var id: Long?,

    @SerializedName("name")
    var name: String,

    @SerializedName("department")
    var department: String?,

    @SerializedName("credit")
    var credit: Int,

    @SerializedName("type")
    var type: String?,
    @SerializedName("students")
    var students: List<Student>?,
    @SerializedName("instructor")
    var instructor: Instructor?,
    @SerializedName("survey")
    var survey: Survey?
) {
    override fun toString(): String {
        return "Course{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", credit=" + credit +
                ", type='" + type + '\'' +
                '}'
    }
}
