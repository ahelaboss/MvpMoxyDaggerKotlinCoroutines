package com.yourgains.mvpmoxydaggertemplate.data.repository

import com.yourgains.mvpmoxydaggertemplate.data.entity.network.request.LoginRequest
import com.yourgains.mvpmoxydaggertemplate.data.network.services.APIAuthServices
import com.yourgains.mvpmoxydaggertemplate.data.storage.preference.TokenPreferenceHelper
import com.yourgains.mvpmoxydaggertemplate.domain.repository.IAuthRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Alexey Shishov
 * on 6.02.20
 */
@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val apiAuthServices: APIAuthServices,
    private val tokenPreferenceHelper: TokenPreferenceHelper
) : IAuthRepository {

    override suspend fun getToken(loginRequest: LoginRequest) {
        val tokenResponse = apiAuthServices.getTokenAsync(loginRequest).await()
        tokenPreferenceHelper.save(tokenResponse.accessToken)
    }

    override suspend fun refreshToken() {
        tokenPreferenceHelper.clear()
        val tokenResponse = apiAuthServices.refreshTokenAsync().await()
        tokenPreferenceHelper.save(tokenResponse.accessToken)
    }

    override suspend fun isTokenExist(): Boolean = !tokenPreferenceHelper.isDefault()

    override suspend fun clear() {
        tokenPreferenceHelper.clear()
    }
}