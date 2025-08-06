package com.spendingviewer.adapter.bank.up.model

data class WebHookResourceResponse(
    val data: WebhookResource
)

data class WebhookResource(
    val type: String, // "webhooks"
    val id: String,
    val attributes: Attributes,
    val relationships: Relationships,
    val links: SelfLink? = null // Optional top-level links object
) {
    data class Attributes(
        val url: String,
        val description: String?, // Nullable
        val secretKey: String? = null, // Optional, only on creation
        val createdAt: String // Typically an ISO 8601 date-time string
    )

    data class Relationships(
        val logs: LogsRelationship
    )

    data class LogsRelationship(
        val links: RelatedLink? = null // Optional
    )

    data class RelatedLink(
        val related: String
    )

    data class SelfLink(
        val self: String
    )
}

