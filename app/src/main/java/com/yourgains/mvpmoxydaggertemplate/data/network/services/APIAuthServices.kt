package com.yourgains.mvpmoxydaggertemplate.data.network.services

import com.yourgains.mvpmoxydaggertemplate.data.entity.network.request.LoginRequest
import com.yourgains.mvpmoxydaggertemplate.data.entity.network.response.LoginResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by Alexey Shishov
 * on 24.07.19
 */
interface APIAuthServices {

    companion object {
        private const val TOKEN = "/oauth/v2/token"
    }

    @POST(TOKEN)
    fun getTokenAsync(@Body loginRequest: LoginRequest): Deferred<LoginResponse>

    @GET(TOKEN)
    fun refreshTokenAsync(): Deferred<LoginResponse>
}