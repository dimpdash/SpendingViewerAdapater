package com.spendingviewer.infrastructure.secrets;

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.nio.charset.StandardCharsets
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.MGF1ParameterSpec
import javax.crypto.Cipher
import javax.crypto.spec.OAEPParameterSpec
import javax.crypto.spec.PSource
import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * Attribution https://medium.com/@mobileatexxeta/how-to-use-asymmetric-encryption-with-android-keystore-013de5cdc745
 */
@Singleton
class KeyStoreFacade @Inject constructor(
        private val keyStore: KeyStore
) {
    companion object {
        const val ANDROID_KEYSTORE = "AndroidKeyStore"
        private const val DEFAULT_KEY = "default_key1"
        private val CHAR_SET = StandardCharsets.UTF_8
        private const val BASE_64_FLAGS = Base64.NO_WRAP
        private const val CIPHER_ALGORITHM = KeyProperties.KEY_ALGORITHM_RSA
        private const val CIPHER_BLOCK_MODE = KeyProperties.BLOCK_MODE_ECB
        private const val CIPHER_PADDING = KeyProperties.ENCRYPTION_PADDING_RSA_OAEP
        private const val CIPHER_DIGEST = KeyProperties.DIGEST_SHA256
        private val CIPHER_ALGORITHM_PARAMETER_SPEC = OAEPParameterSpec(
            CIPHER_DIGEST,
            "MGF1",
            MGF1ParameterSpec.SHA1,
            PSource.PSpecified.DEFAULT
        )
        private const val CIPHER_TRANSFORMATION = "$CIPHER_ALGORITHM/$CIPHER_BLOCK_MODE/$CIPHER_PADDING"
    }

    init {
        // if the key has not been created yet
        if (!keyStore.containsAlias(DEFAULT_KEY)) {
            // generate a new pair
            val keyPairGenerator = KeyPairGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_RSA,
                "AndroidKeyStore"
            )
            val keyGenParameterSpec = KeyGenParameterSpec.Builder(
                DEFAULT_KEY,
                KeyProperties.PURPOSE_ENCRYPT or
                        KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(CIPHER_BLOCK_MODE)
                .setDigests(CIPHER_DIGEST)
                .setEncryptionPaddings(CIPHER_PADDING)
                .build()
            keyPairGenerator.initialize(keyGenParameterSpec)
            keyPairGenerator.generateKeyPair()
        }
    }



    fun encryptWithDefaultKey(value: String): Result<String> {
        val keyEntry = getKeyEntry() ?: return Result.failure(Exception("No default key found"))
        val publicKey = getPublicKey(keyStoreEntry = keyEntry)

        val instance = Cipher.getInstance(CIPHER_TRANSFORMATION)
        instance.init(
            Cipher.ENCRYPT_MODE,
            publicKey,
            CIPHER_ALGORITHM_PARAMETER_SPEC
        )

        return Result.success(Base64.encodeToString(
            instance.doFinal(value.toByteArray(CHAR_SET)), BASE_64_FLAGS
        ))
    }

    fun decryptWithDefaultKey(value: String): String? {
        val keyEntry = getKeyEntry() ?: return null
        val privateKey = getPrivateKey(keyStoreEntry = keyEntry)

        val instance = Cipher.getInstance(CIPHER_TRANSFORMATION)
        instance.init(
            Cipher.DECRYPT_MODE,
            privateKey,
            CIPHER_ALGORITHM_PARAMETER_SPEC
        )
        return String(instance.doFinal(Base64.decode(value, BASE_64_FLAGS)), CHAR_SET)
    }

    private fun getKeyEntry(): KeyStore.PrivateKeyEntry? {
        val entry = keyStore.getEntry(
                DEFAULT_KEY,
                null
        )

        if (entry is KeyStore.PrivateKeyEntry) {
            return entry
        }

        return null
    }

    private fun getPublicKey(keyStoreEntry: KeyStore.PrivateKeyEntry): PublicKey {
        return keyStoreEntry.certificate.publicKey
    }

    private fun getPrivateKey(keyStoreEntry: KeyStore.PrivateKeyEntry): PrivateKey {
        return keyStoreEntry.privateKey
    }
}
