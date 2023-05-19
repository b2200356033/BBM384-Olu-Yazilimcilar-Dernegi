package com.example.oyd.Models

import com.google.gson.annotations.SerializedName

class Survey(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("courseId")
    val courseId: Int?,

    @SerializedName("instructorId")
    val instructorId: Int?,

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