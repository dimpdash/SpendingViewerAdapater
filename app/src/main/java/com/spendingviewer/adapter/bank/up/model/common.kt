package com.spendingviewer.adapter.bank.up.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val data: CategoryData?,
    val links: CategoryLinks?,
) : Parcelable

@Parcelize
data class CategoryData(
    val type: String,
    val id: String
) : Parcelable