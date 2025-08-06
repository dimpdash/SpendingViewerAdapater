package com.spendingviewer.infrastructure.retrofit

fun interface AuthorizationTokenFetcher {
    fun fetchToken(): Result<String>
}