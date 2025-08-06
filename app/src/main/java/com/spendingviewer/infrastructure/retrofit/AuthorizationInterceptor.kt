package com.spendingviewer.infrastructure.retrofit

import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody

class AuthorizationInterceptor(private val tokenFetcher: AuthorizationTokenFetcher) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        tokenFetcher.fetchToken()
            .map { token ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                return chain.proceed(request)
            }.getOrElse { throwable ->
                 return Response.Builder()
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_1)
                    .code(401) // Unauthorized
                    .message(throwable.message ?: "Unauthorized")
                    .body(ResponseBody.create(null, ""))
                    .build()
            }
    }
}