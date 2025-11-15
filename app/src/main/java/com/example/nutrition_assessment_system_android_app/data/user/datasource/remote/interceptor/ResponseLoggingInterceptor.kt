package com.example.nutrition_assessment_system_android_app.data.user.datasource.remote.interceptor

import android.util.Log
import okhttp3.Interceptor
import java.nio.charset.Charset

class ResponseLoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request()
        val response = chain.proceed(request)

        val responseBody = response.body
        val source = responseBody?.source()
        source?.request(Long.MAX_VALUE)
        val buffer = source?.buffer

        val rawJson = buffer?.clone()?.readString(Charset.forName("UTF-8"))
        Log.d("RAW_RESPONSE", "URL: ${request.url}")
        Log.d("RAW_RESPONSE", "Body: $rawJson")

        return response
    }
}