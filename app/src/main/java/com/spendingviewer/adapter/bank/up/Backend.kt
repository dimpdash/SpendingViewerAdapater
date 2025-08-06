package com.spendingviewer.adapter.bank.up

import android.content.Context
import com.spendingviewer.infrastructure.secrets.KeyStoreFacade.Companion.ANDROID_KEYSTORE
import com.spendingviewer.infrastructure.secrets.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.security.KeyStore
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BackendModule {

    @Provides
    @Singleton
    fun provideKeyStore() : KeyStore {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
        keyStore.load(null)
        return keyStore
    }

    @Provides
    @Singleton
    fun provideUpBankApi(tokenManager: TokenManager, upBankApiFactory : UpBankApiFactory) : UpBankApi {
        val tokenProvider = { runCatching {
            tokenManager.retrieveToken(TokenManager.Tokens.UpBank)
                ?: throw Exception("Up Bank Token not found") }
        }

        return upBankApiFactory.createUpBankApi(UpBankApi.BASE_URL, tokenProvider)
    }
}