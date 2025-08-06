package com.spendingviewer.adapter.bank.up.model

import kotlinx.serialization.Serializable

@Serializable
data class WebhookInputResourceRequest(
    val data: WebhookInputResource
)

@Serializable
data class WebhookInputResource(
    val attributes: WebhookInputAttributes
    // Typically, when creating a resource, you don't specify 'type' or 'id' in the request body
    // as the server assigns them. If the API requires 'type' for creation, you'd add it here.
    // val type: String = "webhooks" // Example if type is required
)

@Serializable
data class WebhookInputAttributes(
    val url: String, // Must be a valid HTTP or HTTPS URL, max 300 chars
    val description: String? = null // Nullable and optional, max 64 chars
)
