package com.example.oyd.Models

import com.google.gson.annotations.SerializedName

class Evaluation (
    @SerializedName("id")
    var id: Long?,

    @SerializedName("question_number")
    var name: String,

    @SerializedName("answer")
    var answer: Int,

    @SerializedName("student_courseStudentID")
    var student_courseStudentID: Long,

    @SerializedName("student_courseCourseID")
    var student_courseCourseID: Long
) {
    override fun toString(): String {
        return "Evaluation{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", answer='" + answer + '\'' +
                '}'
    }
}