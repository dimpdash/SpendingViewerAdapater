package com.spendingviewer.adapter.bank.up.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UpAccountsResponse(
    val data: List<AccountResource>,
    val links: PaginationLinks
) : Parcelable

@Parcelize
data class AccountResource(
    val type: String, // Should be "accounts"
    val id: String,
    val attributes: AccountAttributes,
    val relationships: AccountRelationships,
    val links: ResourceLinks? // Optional self link for the resource
) : Parcelable

// Attributes of an account
@Parcelize
data class AccountAttributes(
    val displayName: String,

    val accountType: AccountType,

    val ownershipType: OwnershipType,

    val balance: MoneyObject,

    val createdAt: String
) : Parcelable

@Parcelize
data class AccountRelationships(
    val transactions: TransactionsRelationship
) : Parcelable

@Parcelize
data class TransactionsRelationship(
    val links: RelationshipLinks?
) : Parcelable

enum class AccountType {
    SAVER,
    TRANSACTIONAL,
    HOME_LOAN
}

enum class OwnershipType {
    INDIVIDUAL,
    JOINT
}

// Links specific to a resource (e.g., self link)
@Parcelize
data class ResourceLinks(
    val self: String?
) : Parcelable

// Links within a relationship object (e.g., related link)
@Parcelize
data class RelationshipLinks(
    val related: String?
) : Parcelable

// Links for pagination (prev, next)
@Parcelize
data class PaginationLinks(
    val prev: String?,
    val next: String?
) : Parcelable