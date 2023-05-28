package com.example.oyd.Models

import com.google.gson.annotations.SerializedName

class FileDB (
    @SerializedName("id")
    var id: Long?,
    @SerializedName("file_name")
    var file_name: String?,
    @SerializedName("file")
    var file: ByteArray?
    ){
override fun toString(): String {
    return "File{" +
            "id='" + id + '\'' +
            ", file_name='" + file_name + '\''
            '}';
}
}
