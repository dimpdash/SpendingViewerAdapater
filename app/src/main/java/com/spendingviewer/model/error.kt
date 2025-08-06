package com.spendingviewer.model

sealed class ApiError : Exception() {
    data class NetworkError(val throwable: Throwable) : ApiError()
    data class ServerError(val code: Int, override val message: String?, val url: String?) : ApiError()
    data class UnknownError(val throwable: Throwable) : ApiError()
}