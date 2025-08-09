package com.spendingviewer.adapter.bank.up.model

import android.os.Parcelable
import com.spendingviewer.model.Currency
import com.spendingviewer.model.Money
import com.spendingviewer.model.ValueInBaseUnits
import kotlinx.parcelize.Parcelize

@Parcelize
data class MoneyObject(
    val currencyCode: String,
    val value: String,
    val valueInBaseUnits : ValueInBaseUnits
) : Parcelable {
    fun toMoney() : Money {
        return Money(Currency(currencyCode), valueInBaseUnits)
    }
}