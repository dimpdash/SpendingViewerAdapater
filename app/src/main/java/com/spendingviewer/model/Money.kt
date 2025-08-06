package com.spendingviewer.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlin.math.absoluteValue
import kotlin.math.round

typealias ValueInBaseUnits = Long

fun String.toValueInBaseUnits() : ValueInBaseUnits {
    return this.toLong()
}

fun String.toValueInBaseUnitsOrNull() : ValueInBaseUnits? {
    return this.toLongOrNull()
}

fun UInt.toValueInBaseUnits() : ValueInBaseUnits {
    return this.toLong()
}


@Parcelize
data class Money(val currencyCode: Currency, val valueInBaseUnits: ValueInBaseUnits) : Parcelable {

    fun toRoundedAmount() : Long {
        return if (currencyCode.code == "AUD") {
            return round(valueInBaseUnits / 100.0).toLong()
        } else {
            valueInBaseUnits
        }
    }

    fun from(valueInBaseUnits: ValueInBaseUnits) : Money {
        return Money(currencyCode, valueInBaseUnits)
    }

    fun createZero(): Money {
        return Money(currencyCode, 0)
    }

    fun sameCurrency(other: Money) : Boolean {
        return currencyCode.same(other.currencyCode)
    }

    fun add(other: Money) : Money {
        if (!sameCurrency(other)) {
            throw Exception("Not the same currency")
        }
        return Money(currencyCode, valueInBaseUnits + other.valueInBaseUnits)
    }

    operator fun minus(other: Money): Money {
        if (!sameCurrency(other)) {
            throw Exception("Not the same currency")
        }
        return Money(currencyCode, valueInBaseUnits - other.valueInBaseUnits)
    }

    operator fun times(count: Long): Money {
        return Money(currencyCode, valueInBaseUnits * count.toInt())
    }

    operator fun times(count: Double): Money {
        return Money(currencyCode, (valueInBaseUnits * count).toLong())
    }

    fun fromAndInheritSign(value: UInt): Money {
        val sign = if (valueInBaseUnits < 0) {
            -1
        } else {
            1
        }

        return Money(currencyCode, value.toValueInBaseUnits() * sign)
    }

    operator fun div(size: Int): Money {
        return Money(currencyCode, valueInBaseUnits / size)
    }

    operator fun div(into: UInt): Money {
        return Money(currencyCode, valueInBaseUnits / into.toValueInBaseUnits())
    }

    operator fun rem(into: UInt): Money {
        return Money(currencyCode, valueInBaseUnits % into.toValueInBaseUnits())
    }

    operator fun plus(other: Money): Money? {
        if (!sameCurrency(other)) {
            return null
        }

        return Money(currencyCode, valueInBaseUnits + other.valueInBaseUnits)
    }

    fun divideEvenly(into : UInt) : List<Money> {
        if (into == 0U) {
            return emptyList()
        }

        val amountSplit = this.valueInBaseUnits / into.toInt()
        val remainder = this.valueInBaseUnits % into.toInt()

        val result = mutableListOf<Money>()
        for (i in 1..into.toInt()) {
            result.add(this.from(amountSplit))
        }
        result.add(this.from(amountSplit + remainder))

        assert(result.sumOf { it.valueInBaseUnits } == this.valueInBaseUnits)
        assert(result.size == into.toInt())
        return result
    }

    fun from(valueInBaseUnits: Float): Money {
        return from(valueInBaseUnits.toLong())
    }

    fun isZero(): Boolean {
        return valueInBaseUnits == 0L
    }

    fun isPositive(): Boolean {
        return valueInBaseUnits > 0
    }

    operator fun unaryMinus(): Money {
        return Money(currencyCode, -valueInBaseUnits)
    }
}

inline val Money.absoluteValue: Money
    get()  {
        if (valueInBaseUnits < 0) {
            return Money(currencyCode, valueInBaseUnits.absoluteValue)
        }
        return this
    }

fun <T> Collection<Money>.sum(): Result<Money> {
    if(this.isEmpty()) {
        return Result.success(Money(AUD, 0))
    }

    if (this.map{ it.currencyCode }.toSet().size > 2) {
        return Result.failure(Exception("More than 2 currencies"))
    }

    val collectiveValue : Long = this.sumOf { it.valueInBaseUnits }

    return Result.success(Money(this.first().currencyCode, collectiveValue))
}


@Parcelize
data class Currency(val code : String) : Parcelable {
    fun same(other: Currency) : Boolean {
        return code == other.code
    }
}

val AUD = Currency("AUD")

val Int.dollarsAud get() : Money {
    return Money(AUD, this * 100.toLong())
}


val mockMoney1 = Money(AUD, 10000)