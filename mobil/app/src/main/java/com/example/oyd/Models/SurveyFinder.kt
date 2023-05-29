package com.example.oyd.Models

import com.example.oyd.Users.Instructor
import com.google.gson.annotations.SerializedName

class SurveyFinder(
    @SerializedName("id")
    val id: Long?,

    @SerializedName("courseId")
    val courseId: Long?,

    @SerializedName("instructorId")
    val instructorId: Long?,

    @SerializedName("questions")
    val questions: List<String>
    ) {
        override fun toString(): String {
            return "Survey{" +
                    "id=" + id +
                    ", courseId=" + courseId +
                    ", instructorId=" + instructorId +
                    ", questions=" + questions +
                    '}'
        }
}