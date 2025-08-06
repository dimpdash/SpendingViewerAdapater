package com.spendingviewer.adapter.bank.up.model

data class UpCategory(
    val id: String,
    val name: String,
)

data class CategoriesResponse(
    val data: List<CategoryResponse>
)

data class CategoryResponse(
    val id: String,
    val type: String,
    val attributes: CategoryAttributes,
    val relationships: CategoryRelationships,
    val links: SelfLink
)

data class CategoryAttributes(
    val name: String
)

data class CategoryRelationships(
    val parent: ParentCategoryRelationship?,
    val children: ChildrenCategoryRelationship?
)

data class ParentCategoryRelationship(
    val data: CategoryData?,
    val links: RelatedLink?
)

data class ChildrenCategoryRelationship(
    val data: List<CategoryData>,
    val links: RelatedLink?
)

data class RelatedLink(
    val related : String
)

data class SelfLink(
    val self: String
)

