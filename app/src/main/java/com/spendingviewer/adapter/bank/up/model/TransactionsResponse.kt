package com.spendingviewer.adapter.bank.up.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionsResponse(
    val data: List<UpTransaction>,
    val links: PaginationLinks
) : Parcelable