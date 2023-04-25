package com.example.oyd.Models

import com.google.gson.annotations.SerializedName

class Semester (
    @SerializedName("startDate")
    var startDate: String?,

    @SerializedName("endDate")
    var endDate: String?
    ){
    override fun toString(): String {
        return "Semester{" +
                "startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                "}"
    }
}
