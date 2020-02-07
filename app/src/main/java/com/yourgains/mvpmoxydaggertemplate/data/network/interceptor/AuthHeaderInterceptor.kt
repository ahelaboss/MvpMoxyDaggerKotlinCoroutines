package com.yourgains.mvpmoxydaggertemplate.data.network.interceptor

import com.yourgains.mvpmoxydaggertemplate.data.storage.preference.TokenPreferenceHelper
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthHeaderInterceptor(
    private val tokenPreferenceHelper: TokenPreferenceHelper
) : Interceptor {

    companion object {
        const val HEAD_AUTHORIZATION = "Authorization"
        const val BEARER = "Bearer"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val token = tokenPreferenceHelper.read()
        token?.let { request = getRequest(request, token) }
        return chain.proceed(request)
    }

    private fun getRequest(request: Request, token: String): Request =
        request.newBuilder().addHeader(HEAD_AUTHORIZATION, "$BEARER $token").build()
}