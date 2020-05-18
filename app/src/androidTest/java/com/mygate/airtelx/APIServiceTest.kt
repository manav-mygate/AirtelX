package com.mygate.airtelx

import android.content.Context
import android.util.Log
import com.mygate.airtelx.Constants.CITY
import com.mygate.airtelx.engine.ApiInterface
import com.squareup.okhttp.mockwebserver.Dispatcher
import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.MockWebServer
import com.squareup.okhttp.mockwebserver.RecordedRequest
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.nio.charset.StandardCharsets.UTF_8


class APIServiceTest {
    private var mockWebServer = MockWebServer()

    private lateinit var apiService: ApiInterface

    @Before
    fun setup() {
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
                val requestBuilder = original.newBuilder()
                requestBuilder.header("Content-Type", "application/json")
                requestBuilder.header("Accept", "application/json")
                    .method(original.method, original.body)
                val request = requestBuilder.build()
                return chain.proceed(request)
            }
        })
        httpClient.addInterceptor(interceptor)

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/").url())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create(ApiInterface::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testAppVersions() {
        // Assign
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(readContentFromFilePath(AppController.applicationContext()))
        mockWebServer.enqueue(response)

        // Act
        val product = apiService.getSearchList("airtel",CITY)?.execute();

        val dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest?): MockResponse {
                if (request?.path == "/compassLocation/rest/address/autocomplete") {
                    return MockResponse()
                        .setResponseCode(HttpURLConnection.HTTP_OK)
                        .setBody(readContentFromFilePath(AppController.applicationContext()))

                }
                return MockResponse().setResponseCode(404);
            }

        }
        mockWebServer.setDispatcher(dispatcher)
    }

    private val sLogger: HttpLoggingInterceptor.Logger =
        object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d("Retrofit", message)
            }
        }


    fun readContentFromFilePath(context: Context): String? {
        var json: String? = null
        json = try {
            val istr: InputStream = context.getAssets().open("test.json")
            val size: Int = istr.available()
            val buffer = ByteArray(size)
            istr.read(buffer)
            istr.close()
            String(buffer, UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

}