package com.spendingviewer.infrastructure.secrets

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val keyStoreFacade: KeyStoreFacade
) {

    enum class Tokens {
        UpBank,
        BasiqAccessToken
    }

    val sharedPrefs : SharedPreferences

    init {
        sharedPrefs = EncryptedSharedPreferences.create(
            //Excluded in device backup in backup_rules.xml
                "encrypted_prefs",
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }


    fun storeToken(tokenType: Tokens, token: String) : Result<Unit> {
        val encryptedToken = keyStoreFacade.encryptWithDefaultKey(token)
        if (encryptedToken.isFailure) {
            return Result.failure(encryptedToken.exceptionOrNull()!!)
        }

//         Store encryptedToken securely, e.g., in SharedPreferences with a secure flag
        val editor = sharedPrefs.edit()
        editor.putString(tokenType.toString(), encryptedToken.getOrThrow())
        editor.apply()

        return Result.success(Unit)
    }

    fun retrieveToken(tokenType: Tokens): String? {
        // Retrieve encryptedToken from secure storage
        val encryptedToken = sharedPrefs.getString(tokenType.toString(), null) ?: return null

        return keyStoreFacade.decryptWithDefaultKey(encryptedToken)
    }
}