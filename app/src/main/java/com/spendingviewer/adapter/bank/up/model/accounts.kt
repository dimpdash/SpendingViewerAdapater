package com.spendingviewer.adapter.bank.up.model

data class UpAccountsResponse(
    val data: List<AccountResource>,
    val links: PaginationLinks
)

data class AccountResource(
    val type: String, // Should be "accounts"
    val id: String,
    val attributes: AccountAttributes,
    val relationships: AccountRelationships,
    val links: ResourceLinks? // Optional self link for the resource
)

// Attributes of an account
data class AccountAttributes(
    val displayName: String,

    val accountType: AccountType,

    val ownershipType: OwnershipType,

    val balance: MoneyObject,

    val createdAt: String
)

data class AccountRelationships(
    val transactions: TransactionsRelationship
)

data class TransactionsRelationship(
    val links: RelationshipLinks?
)

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
data class ResourceLinks(
    val self: String?
)

// Links within a relationship object (e.g., related link)
data class RelationshipLinks(
    val related: String?
)

// Links for pagination (prev, next)
data class PaginationLinks(
    val prev: String?,
    val next: String?
)