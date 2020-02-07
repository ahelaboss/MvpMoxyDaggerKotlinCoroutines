package com.yourgains.mvpmoxydaggertemplate.domain.usercase

import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.yourgains.mvpmoxydaggertemplate.data.entity.presentation.NetworkErrorUiModel
import com.yourgains.mvpmoxydaggertemplate.data.network.error.FieldsDeserializer
import com.yourgains.mvpmoxydaggertemplate.data.network.error.VariableError
import com.yourgains.mvpmoxydaggertemplate.domain.repository.IAuthRepository
import com.yourgains.mvpmoxydaggertemplate.domain.usercase.blocks.CompletionBlock
import kotlinx.coroutines.*
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Created by Alexey Shishov
 * on 24.07.19
 */

abstract class BaseCoroutinesUseCase<T> {

    @Inject
    lateinit var authRepository: IAuthRepository

    private var parentJob: Job = Job()
    private var backgroundContext: CoroutineContext = Dispatchers.IO
    private var foregroundContext: CoroutineContext = Dispatchers.Main

    private var delay: Long = 0L

    fun setDelay(delay: Long) {
        this.delay = delay
    }

    protected open fun executePreBackground() {
        //do nothing
    }

    protected open fun executePostBackground() {
        //do nothing
    }

    protected abstract suspend fun executeOnBackground(): T

    fun execute(block: CompletionBlock<T>) {
        val response = Request<T>().apply { block() }
        unsubscribe()
        parentJob = Job()
        CoroutineScope(foregroundContext + parentJob).launch { launchScope(response) }
    }

    private suspend fun launchScope(response: Request<T>, isRetry: Boolean = true) {
        if (delay > 0) delay(delay)
        executePreBackground()
        try {
            val result = withContext(backgroundContext) {
                executeOnBackground()
            }
            response(result)
        } catch (ex: CancellationException) {
            Timber.d(ex)
            response(ex)
        } catch (ex: HttpException) {
            val error = getError(ex)
            Timber.e(error.toString())
            when {
                isRetry && error.isTokenExpireError() && authRepository.isTokenExist() -> try {
                    authRepository.refreshToken()
                    launchScope(response, false)
                } catch (ex: HttpException) {
//                    authRepository.logout()
                    response(getError(ex))
                }
                else -> response(error)
            }
        } catch (ex: Exception) {
            Timber.e(ex)
            response(ex)
        } finally {
            executePostBackground()
        }
    }

    fun unsubscribe() {
        parentJob.apply {
            cancelChildren()
            cancel()
        }
    }

    private fun getError(ex: HttpException): NetworkErrorUiModel {
        val responseBody = ex.response()?.errorBody()
        return takeIf { responseBody?.contentType()?.subtype() == "json" }?.let {
            try {
                val gson = GsonBuilder().registerTypeAdapter(
                    VariableError::class.java,
                    FieldsDeserializer()
                ).create()
                val error = gson.fromJson(responseBody?.string(), VariableError::class.java)
                NetworkErrorUiModel(ex.code(), error.message, error)
            } catch (jsonEx: JsonSyntaxException) {
                Timber.e(jsonEx)
                NetworkErrorUiModel(ex.code(), ex.message())
            }
        } ?: NetworkErrorUiModel(
            ex.code(),
            ex.message()
        )
    }

    class Request<T> {
        private var onComplete: ((T) -> Unit)? = null
        private var onNetworkError: ((NetworkErrorUiModel) -> Unit)? = null
        private var onError: ((Exception) -> Unit)? = null
        private var onCancel: ((CancellationException) -> Unit)? = null

        fun onComplete(block: (T) -> Unit) {
            onComplete = block
        }

        fun onNetworkError(block: (NetworkErrorUiModel) -> Unit) {
            onNetworkError = block
        }

        fun onError(block: (Exception) -> Unit) {
            onError = block
        }

        fun onCancel(block: (CancellationException) -> Unit) {
            onCancel = block
        }

        operator fun invoke(result: T) {
            onComplete?.invoke(result)
        }

        operator fun invoke(error: NetworkErrorUiModel) {
            onNetworkError?.invoke(error)
        }

        operator fun invoke(error: Exception) {
            onError?.invoke(error)
        }

        operator fun invoke(error: CancellationException) {
            onCancel?.invoke(error)
        }

    }
}