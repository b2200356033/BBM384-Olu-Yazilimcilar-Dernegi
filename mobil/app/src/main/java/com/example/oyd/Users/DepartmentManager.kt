package com.example.oyd.Users

import com.google.gson.annotations.SerializedName

class DepartmentManager(
    @SerializedName("name")
    var name: String?,

    @SerializedName("surname")
    var surname: String?,

    @SerializedName("email")
    var email: String?,

    @SerializedName("password")
    var password: String?,

    @SerializedName("photo")
    var photo: String?
) {
    override fun toString(): String {
        return "DepartmentManager{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email=" + email + '\'' +
                ", password='" + password + '\'' +
                ", photo='" + photo + '\'' +
                '}'
    }
}