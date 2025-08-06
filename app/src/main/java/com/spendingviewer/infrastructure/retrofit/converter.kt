package com.spendingviewer.infrastructure.retrofit

import com.spendingviewer.adapter.bank.up.model.UpTransactionId
import retrofit2.Converter
import retrofit2.Retrofit
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.lang.reflect.Type


class ZoneDateTimeConverterFactory : Converter.Factory() {
    override fun stringConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, String>? {
        if (type == ZonedDateTime::class.java) {
            return ZonedDateTimeConverter()
        }
        return null
    }
}

class ZonedDateTimeConverter : Converter<ZonedDateTime, String> {
    override fun convert(value: ZonedDateTime): String? {
        //format should be 2020-01-01T01:02:03+10:00
        // needs to be rfc3339 compliant
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")
        return value.format(formatter)
    }
}

class UpBankApiModelConverterFactory : Converter.Factory() {
    override fun stringConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, String>? {
        if (type == UpTransactionId::class.java) {
            return ZonedDateTimeConverter()
        }
        return null
    }
}

class UpTransactionIdConverter : Converter<UpTransactionId, String> {
    override fun convert(value: UpTransactionId): String? {
        return value.value
    }
}

