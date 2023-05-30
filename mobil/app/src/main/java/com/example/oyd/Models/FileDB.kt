package com.example.oyd.Models

import com.google.gson.annotations.SerializedName
import java.util.Base64

class FileDB(
    @SerializedName("id")
    var id: Long?,
    @SerializedName("file_name")
    var file_name: String?,
    @SerializedName("file")
    var file: String?
    ){
override fun toString(): String {
    return "File{" +
            "id='" + id + '\'' +
            ", file_name='" + file_name + '\''
            '}';
}


}
