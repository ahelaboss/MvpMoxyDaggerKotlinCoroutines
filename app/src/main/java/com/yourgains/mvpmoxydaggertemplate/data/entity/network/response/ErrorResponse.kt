package com.yourgains.mvpmoxydaggertemplate.data.entity.network.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("message") val message: String?
)