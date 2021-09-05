package com.elacqua.soccerleague.data.remote

import com.elacqua.soccerleague.data.remote.service.FootballService
import com.elacqua.soccerleague.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FootballApi {
    @Volatile
    private lateinit var instance: Retrofit
    private lateinit var okHttpClient: OkHttpClient

    private fun getInstance(): Retrofit {
        synchronized(this) {
            if (!::instance.isInitialized) {
                instance = Retrofit.Builder()
                    .baseUrl(Constants.FOOTBALL_DATA_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkHttpClient())
                    .build()
            }
            return instance
        }
    }

    private fun getOkHttpClient(): OkHttpClient {
        if (!::okHttpClient.isInitialized) {
            val builder = OkHttpClient.Builder()
            val logger = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)

            builder
                .addInterceptor { chain ->
                    val request =
                        chain.request().newBuilder().addHeader(
                            "X-Auth-Token",
                            Constants.FOOTBALL_DATA_API_KEY
                        ).build()
                    chain.proceed(request)
                }
                .addInterceptor(logger)
            okHttpClient = builder.build()
        }
        return okHttpClient
    }

    fun getFootballService(): FootballService {
        return if (!this::instance.isInitialized) {
            getInstance().create(FootballService::class.java)
        } else {
            instance.create(FootballService::class.java)
        }
    }
}