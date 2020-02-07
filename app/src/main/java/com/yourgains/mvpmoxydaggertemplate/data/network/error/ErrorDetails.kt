package com.yourgains.mvpmoxydaggertemplate.data.network.error

import com.google.gson.annotations.SerializedName

class ErrorDetails(
    @SerializedName("error")
    var error: String? = null,
    @SerializedName("description")
    var description: String? = null
)