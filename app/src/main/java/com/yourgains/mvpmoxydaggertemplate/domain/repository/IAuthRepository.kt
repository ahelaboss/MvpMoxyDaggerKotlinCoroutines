package com.yourgains.mvpmoxydaggertemplate.domain.repository

import com.yourgains.mvpmoxydaggertemplate.data.entity.network.request.LoginRequest

interface IAuthRepository {

    suspend fun getToken(loginRequest: LoginRequest)

    suspend fun refreshToken()

    suspend fun isTokenExist(): Boolean

    suspend fun clear()
}