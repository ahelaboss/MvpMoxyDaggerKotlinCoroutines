package com.yourgains.mvpmoxydaggertemplate.data.entity.network.request

import com.google.gson.annotations.SerializedName

open class LoginRequest(
    @SerializedName("login") val login: String,
    @SerializedName("password") val password: String
)
