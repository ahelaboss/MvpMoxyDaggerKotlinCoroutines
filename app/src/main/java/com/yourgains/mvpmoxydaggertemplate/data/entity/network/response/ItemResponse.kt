package com.yourgains.mvpmoxydaggertemplate.data.entity.network.response

import com.google.gson.annotations.SerializedName

data class ItemResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)