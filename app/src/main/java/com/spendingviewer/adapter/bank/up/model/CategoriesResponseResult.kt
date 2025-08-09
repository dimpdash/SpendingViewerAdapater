package com.spendingviewer.adapter.bank.up.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoriesResponseResult(
    val success: CategoriesResponse?,
    val error: AidlError?
) : Parcelable