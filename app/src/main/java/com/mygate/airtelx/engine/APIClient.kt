package com.mygate.airtelx.engine

import android.util.Log
import com.mygate.airtelx.BuildConfig
import com.mygate.airtelx.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object APIClient {
    private var retrofit: Retrofit? = null

    val client: Retrofit?
        get() {
            val interceptor = HttpLoggingInterceptor(sLogger)
            if (BuildConfig.DEBUG) {
                interceptor.level = HttpLoggingInterceptor.Level.BODY
            } else {
                interceptor.level = HttpLoggingInterceptor.Level.BASIC
            }
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    val original = chain.request()
                    var requestBuilder = original.newBuilder()
                    requestBuilder.header("Content-Type", "application/json")
                    requestBuilder.header("Accept", "application/json")
                        .method(original.method, original.body)
                    val request = requestBuilder.build()
                    return chain.proceed(request)
                }
            })
            httpClient.addInterceptor(interceptor)
            retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
            return retrofit
        }

    private val sLogger: HttpLoggingInterceptor.Logger =
        object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d("Retrofit", message)
            }
        }
}