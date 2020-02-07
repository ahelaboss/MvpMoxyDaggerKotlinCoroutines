package com.yourgains.mvpmoxydaggertemplate.common

import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.Headers
import com.bumptech.glide.load.model.LazyHeaders
import com.yourgains.mvpmoxydaggertemplate.App
import com.yourgains.mvpmoxydaggertemplate.data.network.interceptor.AuthHeaderInterceptor.Companion.BEARER
import com.yourgains.mvpmoxydaggertemplate.data.network.interceptor.AuthHeaderInterceptor.Companion.HEAD_AUTHORIZATION

object GlideUtil {

    fun getGlideUrl(url: String): GlideUrl = GlideUrl(url, getHeaders())

    private fun getHeaders(): Headers {
        val tokenPreferenceHelper = App.appComponent.getTokenPreferenceHelper()
        return LazyHeaders.Builder()
            .addHeader(HEAD_AUTHORIZATION, "$BEARER ${tokenPreferenceHelper.read()}")
            .build()
    }
}