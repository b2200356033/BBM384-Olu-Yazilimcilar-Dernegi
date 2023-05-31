package com.example.oyd.Models

import com.example.oyd.Users.Instructor
import com.google.gson.annotations.SerializedName

class Survey(
    @SerializedName("id")
    val id: Long?,

    @SerializedName("courseId")
    val course: Course?,

    @SerializedName("questions")
    val questionList: List<String>
) {
    override fun toString(): String {
        return "Survey{" +
                "id=" + id +
                ", courseId=" + course +
                ", questions=" + questionList +
                '}'
    }
}