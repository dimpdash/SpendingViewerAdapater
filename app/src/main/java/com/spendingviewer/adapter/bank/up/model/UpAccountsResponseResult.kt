package com.spendingviewer.adapter.bank.up.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UpAccountsResponseResult(
    val success: UpAccountsResponse?,
    val error: AidlError?
) : Parcelable