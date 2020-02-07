package com.yourgains.mvpmoxydaggertemplate.common

import android.content.Context
import android.net.ConnectivityManager
import com.yourgains.mvpmoxydaggertemplate.App
import java.net.URLEncoder

object NetworkUtils {

    const val NO_ID = -1L

    const val JSON_CONTENT_TYPE = "application/json"

    const val EXPAND_DIVIDER: String = ","
    const val PAGE: String = "page"
    const val QUERY: String = "query"
    const val ID: String = "id"
    const val NAME = "name"
    const val API: String = "/api"

    const val ERROR_BAD_REQUEST = 400
    const val ERROR_ACCESS_DENIED = 401
    const val ERROR_FORBIDDEN = 403
    const val ERROR_NOT_FOUND = 404
    const val ERROR_ENTITY = 422

    const val UTF_8 = "utf-8"

    fun isOnline(): Boolean {
        val context = App.appComponent.context()
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm?.activeNetworkInfo?.isConnected == true
    }

    fun getSearchQuery(query: String): String = URLEncoder.encode("*$query*", UTF_8)
}