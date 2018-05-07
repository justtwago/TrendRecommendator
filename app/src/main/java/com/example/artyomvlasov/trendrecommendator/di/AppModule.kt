package com.example.artyomvlasov.trendrecommendator.di

import android.app.Application
import android.content.Context
import com.example.artyomvlasov.trendrecommendator.BuildConfig
import com.example.artyomvlasov.trendrecommendator.repositories.ClothesRepository
import com.example.artyomvlasov.trendrecommendator.repositories.ClothesRepositoryImpl
import com.example.artyomvlasov.trendrecommendator.services.ClothesService

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val ACCEPT = "Accept"
private const val APPLICATION_JSON = "application/json"
private const val BASE_API_URL = "http://api.shopstyle.com/api/v2/"

@Module
internal class AppModule {
    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application

    @Singleton
    @Provides
    fun provideClothesRepository(clothesService: ClothesService): ClothesRepository = ClothesRepositoryImpl(clothesService)

    @Provides
    fun provideClothesService(httpClient: OkHttpClient) =
            getRetrofitBuilder(httpClient)
                    .create(ClothesService::class.java)!!

    private fun getRetrofitBuilder(httpClient: OkHttpClient) =
            Retrofit.Builder()
                    .baseUrl(BASE_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .client(httpClient)
                    .build()


    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
            OkHttpClient.Builder()
                    .addHttpHeaders()
                    .addHttpLoggingInterceptorIfDebugBuildConfig()
                    .build()

    private fun OkHttpClient.Builder.addHttpHeaders() =
            addInterceptor {
                it.proceed(it.request().newBuilder().header(ACCEPT, APPLICATION_JSON).build())
            }

    private fun OkHttpClient.Builder.addHttpLoggingInterceptorIfDebugBuildConfig(): OkHttpClient.Builder {
        if (BuildConfig.DEBUG) {
            addInterceptor(getHttpLoggingInterceptor())
        }
        return this
    }

    private fun getHttpLoggingInterceptor() =
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
}