package com.spendingviewer.adapter.bank.up.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UpCategory(
    val id: String,
    val name: String,
) : Parcelable

@Parcelize
data class CategoriesResponse(
    val data: List<CategoryResponse>
) : Parcelable

@Parcelize
data class CategoryResponse(
    val id: String,
    val type: String,
    val attributes: CategoryAttributes,
    val relationships: CategoryRelationships,
    val links: SelfLink
) : Parcelable

@Parcelize
data class CategoryAttributes(
    val name: String
) : Parcelable

@Parcelize
data class CategoryRelationships(
    val parent: ParentCategoryRelationship?,
    val children: ChildrenCategoryRelationship?
) : Parcelable

@Parcelize
data class ParentCategoryRelationship(
    val data: CategoryData?,
    val links: RelatedLink?
) : Parcelable

@Parcelize
data class ChildrenCategoryRelationship(
    val data: List<CategoryData>,
    val links: RelatedLink?
) : Parcelable

@Parcelize
data class RelatedLink(
    val related : String
) : Parcelable

@Parcelize
data class SelfLink(
    val self: String
) : Parcelable

