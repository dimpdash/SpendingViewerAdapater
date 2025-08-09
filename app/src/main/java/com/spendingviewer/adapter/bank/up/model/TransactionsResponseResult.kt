package com.spendingviewer.adapter.bank.up.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionsResponseResult(
    val success: TransactionsResponse?,
    val error: AidlError?
) : Parcelable