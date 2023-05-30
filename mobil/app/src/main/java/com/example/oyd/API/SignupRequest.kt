package com.example.oyd.API

data class SignupRequest(
    var password: String?,
    var name: String,
    var surname: String,
    var email: String,
    var photo: String?,
)
