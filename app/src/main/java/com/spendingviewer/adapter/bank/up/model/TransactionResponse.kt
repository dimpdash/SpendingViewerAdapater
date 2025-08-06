package com.spendingviewer.adapter.bank.up.model

import com.google.gson.annotations.JsonAdapter
import com.spendingviewer.adapter.bank.up.UpTransactionIdTypeAdapter
import java.time.ZonedDateTime

data class UpTransaction (
    val type : String,
    val id : UpTransactionId,
    val attributes : Attributes,
    val relationships: Relationships,
    val links: UpTransactionLinks,
) {
    fun isInternalTransaction() : Boolean {
        return relationships.transferAccount.isInternalTransaction()
    }
}

data class Attributes (
    val status: TransactionStatusEnum,
    val rawText: String?,
    val description: String,
    val message: String?,
    val isCategorizable: Boolean,
    val holdInfo: HoldInfoObject?,
    val roundUp: RoundUpObject?,
    val cashback: CashbackObject?,
    val amount: MoneyObject,
    val foreignAmount: MoneyObject?,
    val cardPurchaseMethod: CardPurchaseMethodObject?,
    val settledAt: ZonedDateTime?,
    val createdAt: ZonedDateTime?,
    val transactionType: String?,
    val note: NoteObject?,
    val performingCustomer: CustomerObject?,
    val deepLinkURL: String,
)


data class UpTransactionLinks(
    val self: String,
    val related: String
) 

data class Tags(
    val data: List<TagData>,
    val links: TagLinks,
) 

data class TagData(
    val type: String,
    val id: String
) 

data class TagLinks(
    val self: String,
) 

data class CategoryLinks(
    val self: String,
    val related: String
) 

data class Relationships(
    val account: Account,
    val transferAccount: TransferAccount,
    val category: Category?,
    val parentCategory: Category?,
    val tags: Tags,
    val attachment: Attachment
)

data class Attachment(
    val data: AttachmentData?,
    val links: AttachmentLinks?,
)

data class AttachmentData(
    val type: String,
    val id: String
) 

data class AttachmentLinks(
    val related: String
) 

data class Account(
    val data: AccountData,
    val links: AccountLinks?,
)

data class TransferAccount(
    val data: AccountData?,
    val links: AccountLinks?,
) {
    fun isInternalTransaction() : Boolean {
        return data != null
    }
}

data class AccountData(
    val type: String,
    val id: String
) 

data class AccountLinks(
    val related: String
) 

data class CustomerObject(
    val displayName: String
) 

data class NoteObject(
    val text: String
) 

data class CardPurchaseMethodObject(
    val method : CardPurchaseMethodEnum,
    val cardNumberSuffix: String?
) 


enum class CardPurchaseMethodEnum{
    BAR_CODE, OCR, CARD_PIN, CARD_DETAILS, CARD_ON_FILE, ECOMMERCE, MAGNETIC_STRIPE, CONTACTLESS
}

data class CashbackObject(
    val amount: MoneyObject,
    val boostPortion: MoneyObject,
)

data class RoundUpObject(
    val amount: MoneyObject,
    val boostPortion: MoneyObject,
)

data class HoldInfoObject(
    val amount: MoneyObject,
    val foreignAmount: MoneyObject?,
)

@JsonAdapter(UpTransactionIdTypeAdapter::class)
data class UpTransactionId(val value : String)

enum class TransactionStatusEnum {
    HELD,
    SETTLED
}
