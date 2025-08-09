package com.spendingviewer.adapter.bank.up.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AidlError(
    val message: String?,
    val stackTrace: String?
) : Parcelable