package com.yourgains.mvpmoxydaggertemplate.data.entity.presentation

import com.yourgains.mvpmoxydaggertemplate.common.NetworkUtils.ERROR_ACCESS_DENIED
import com.yourgains.mvpmoxydaggertemplate.data.network.error.VariableError

data class NetworkErrorUiModel(
    val code: Int,
    val message: String? = null,
    val variableError: VariableError? = null
) {

    fun isTokenExpireError(): Boolean = code == ERROR_ACCESS_DENIED

}