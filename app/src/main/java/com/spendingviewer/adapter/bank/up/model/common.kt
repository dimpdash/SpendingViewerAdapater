package com.spendingviewer.adapter.bank.up.model

data class Category(
    val data: CategoryData?,
    val links: CategoryLinks?,
)

data class CategoryData(
    val type: String,
    val id: String
)