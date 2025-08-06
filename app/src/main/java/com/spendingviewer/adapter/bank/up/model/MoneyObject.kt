package com.spendingviewer.adapter.bank.up.model

import com.spendingviewer.model.Currency
import com.spendingviewer.model.Money
import com.spendingviewer.model.ValueInBaseUnits

data class MoneyObject(
    val currencyCode: String,
    val value: String,
    val valueInBaseUnits : ValueInBaseUnits
) {
    fun toMoney() : Money {
        return Money(Currency(currencyCode), valueInBaseUnits)
    }
}