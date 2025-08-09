package com.spendingviewer.adapter.bank.up.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionResponseResult(
    val success: TransactionResponse?,
    val error: AidlError?
) : Parcelable