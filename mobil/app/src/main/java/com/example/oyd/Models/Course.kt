package com.example.oyd.Models

import com.google.gson.annotations.SerializedName

class Course(
    @SerializedName("name")
    var name: String,

    @SerializedName("department")
    var department: String?,

    @SerializedName("credit")
    var credit: Int,

    @SerializedName("type")
    var type: String?
) {
    override fun toString(): String {
        return "Course{" +
                "name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", credit=" + credit +
                ", type='" + type + '\'' +
                '}'
    }
}

