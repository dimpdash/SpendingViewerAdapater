package com.spendingviewer.adapter.bank.up;

import com.spendingviewer.infrastructure.retrofit.AuthorizationInterceptor
import com.spendingviewer.infrastructure.retrofit.AuthorizationTokenFetcher
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.ZonedDateTime;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
class UpBankApiFactory @Inject constructor(
) {

    fun getGson() : Gson {
        return GsonBuilder()
                .registerTypeAdapter(ZonedDateTime::class.java, ZonedDateTimeTypeAdapter())
                .serializeNulls()
                .setPrettyPrinting()
        .create();
    }

    fun createUpBankApi(upBankUrl :String, token : AuthorizationTokenFetcher): UpBankApi {
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(AuthorizationInterceptor(token))
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(upBankUrl)
                .client(okHttpClient)
                .addConverterFactory(UpTransactionIdConverterFactory())
//        .addConverterFactory(ZoneDateTimeConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .build()

        return retrofit.create(UpBankApi::class.java)
    }
}

