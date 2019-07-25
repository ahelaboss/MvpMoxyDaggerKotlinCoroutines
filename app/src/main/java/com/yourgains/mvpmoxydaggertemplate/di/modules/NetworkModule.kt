package com.yourgains.mvpmoxydaggertemplate.di.modules

import android.text.format.DateUtils
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.yourgains.mvpmoxydaggertemplate.BuildConfig
import com.yourgains.mvpmoxydaggertemplate.data.network.ApiRepository
import com.yourgains.mvpmoxydaggertemplate.data.network.IApiRepository
import com.yourgains.mvpmoxydaggertemplate.data.network.services.ApiServices
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Alexey Shishov
 * on 24.07.19
 */
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideGsonConvertorFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
            .connectTimeout(DateUtils.MINUTE_IN_MILLIS, TimeUnit.MILLISECONDS)
            .writeTimeout(DateUtils.MINUTE_IN_MILLIS, TimeUnit.MILLISECONDS)
            .readTimeout(DateUtils.MINUTE_IN_MILLIS, TimeUnit.MILLISECONDS)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        client.addInterceptor(interceptor)
        return client.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        factory: GsonConverterFactory,
        client: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(factory)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(client)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiServices = retrofit.create(ApiServices::class.java)

    @Provides
    @Singleton
    fun provideApiRepository(apiServices: ApiServices): IApiRepository = ApiRepository(apiServices)
}