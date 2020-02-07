package com.yourgains.mvpmoxydaggertemplate.di.modules

import android.text.format.DateUtils
import com.google.gson.Gson
import com.yourgains.mvpmoxydaggertemplate.BuildConfig
import com.yourgains.mvpmoxydaggertemplate.data.network.adapter.NullBodyCoroutineCallAdapterFactory
import com.yourgains.mvpmoxydaggertemplate.data.network.interceptor.AuthHeaderInterceptor
import com.yourgains.mvpmoxydaggertemplate.data.network.services.APIAuthServices
import com.yourgains.mvpmoxydaggertemplate.data.network.services.APIServices
import com.yourgains.mvpmoxydaggertemplate.data.storage.preference.TokenPreferenceHelper
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
    fun provideOkHttpClient(tokenHelper: TokenPreferenceHelper): OkHttpClient {
        val client = OkHttpClient.Builder()
            .connectTimeout(DateUtils.MINUTE_IN_MILLIS, TimeUnit.MILLISECONDS)
            .writeTimeout(DateUtils.MINUTE_IN_MILLIS, TimeUnit.MILLISECONDS)
            .readTimeout(DateUtils.MINUTE_IN_MILLIS, TimeUnit.MILLISECONDS)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        client.addInterceptor(interceptor)
        client.addInterceptor(AuthHeaderInterceptor(tokenHelper))
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
        .addCallAdapterFactory(NullBodyCoroutineCallAdapterFactory())
        .client(client)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): APIServices =
        retrofit.create(APIServices::class.java)

    @Provides
    @Singleton
    fun provideApiAuthServices(retrofit: Retrofit): APIAuthServices =
        retrofit.create(APIAuthServices::class.java)
}