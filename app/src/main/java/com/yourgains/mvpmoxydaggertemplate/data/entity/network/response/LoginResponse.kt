package com.yourgains.mvpmoxydaggertemplate.data.entity.network.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("access_token") val accessToken: String
)