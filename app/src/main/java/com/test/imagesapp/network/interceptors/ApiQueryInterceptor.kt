package com.test.imagesapp.network.interceptors

import android.util.Log
import com.test.imagesapp.network.data.ApiConstants
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ApiQueryInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url.newBuilder()
            .addQueryParameter("api_key", ApiConstants.API_KEY)
            .addQueryParameter("format", ApiConstants.API_FORMAT)
            .addQueryParameter("nojsoncallback", ApiConstants.API_JSON_CALLBACK)
            .build()

        val request = Request.Builder().url(url).build()
        return chain.proceed(request)
    }

}