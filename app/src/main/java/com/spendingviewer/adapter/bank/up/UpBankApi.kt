package com.spendingviewer.adapter.bank.up

import com.spendingviewer.adapter.bank.up.model.AccountType
import com.spendingviewer.adapter.bank.up.model.CategoriesResponse
import com.spendingviewer.adapter.bank.up.model.OwnershipType
import com.spendingviewer.adapter.bank.up.model.UpAccountsResponse
import com.spendingviewer.adapter.bank.up.model.UpTransaction
import com.spendingviewer.adapter.bank.up.model.UpTransactionId
import com.spendingviewer.adapter.bank.up.model.WebHookResourceResponse
import com.spendingviewer.adapter.bank.up.model.WebhookInputResourceRequest
import com.spendingviewer.model.ApiError
import retrofit2.Call
import retrofit2.awaitResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface UpBankApi {
    companion object {
        const val BASE_URL = "https://api.up.com.au/"
    }

    @GET("api/v1/transactions")
    fun getTransactions(
        @Query("page[size]") pageSize: Int,
        @Query("filter[tag]") tag: String? = null,
        @Query("filter[status]") status: String? = null,
//        @Query("filter[since]") since: ZonedDateTime?,
//        @Query("filter[until]") until: ZonedDateTime?,
        @Query("filter[since]") since: String? = null,
        @Query("filter[until]") until: String? = null,
        @Query("filter[category]") category: String? = null
    ): Call<TransactionsResponse>

    @GET
    fun getTransactionsPage(@Url link: String): Call<TransactionsResponse>

    @GET("api/v1/transactions/{id}")
    fun getTransaction(
        @Path("id") id: UpTransactionId
    ) : Call<TransactionResponse>

    @GET("api/v1/categories")
    fun getCategories() : Call<CategoriesResponse>

    @GET("api/v1/webhooks/{id}")
    fun getWebhook(
        @Path("id") id: String
    ) : Call<WebHookResourceResponse>

    @POST("api/v1/webhooks")
    fun createWebhook(request: WebhookInputResourceRequest) : Call<WebHookResourceResponse>

    @GET("api/v1/accounts")
    fun getAccounts(
        @Query("page[size]") pageSize: Int,
        @Query("filter[account_type]") accountType: AccountType? = null,
        @Query("filter[ownership_type]") ownershipType: OwnershipType? = null
    ) : Call<UpAccountsResponse>

    @GET
    fun getAccountsPage(@Url link: String): Call<UpAccountsResponse>
}

data class TransactionsResponse(
    val data: List<UpTransaction>,
    val links: PaginationLinks
)

data class TransactionResponse(
    val data: UpTransaction
)

data class PaginationLinks(
    val prev: String,
    val next: String,
)


suspend fun <T> Call<T>.getResult(): Result<T> {
    val awaitedResponse = this.awaitResponse()
    try {
        if (!awaitedResponse.isSuccessful) {
            return Result.failure(
                ApiError.ServerError(
                    awaitedResponse.code(),
                    awaitedResponse.errorBody()?.string(),
                    awaitedResponse.raw().request().url().toString()
                )
            )
        }

        val body = awaitedResponse.body() ?: return Result.failure(Throwable("Failed to fetch from body"))
        return Result.success(body)
    } catch (e: Exception) {
        return Result.failure(e)
    }
}

