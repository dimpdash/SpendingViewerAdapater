package com.spendingviewer.adapter.bank.up

import java.lang.reflect.Type
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.spendingviewer.adapter.bank.up.model.UpTransactionId
import retrofit2.Converter
import retrofit2.Retrofit
import java.time.ZonedDateTime

class UpTransactionIdTypeAdapter : TypeAdapter<UpTransactionId>() {

    override fun write(out: JsonWriter?, value: UpTransactionId?) {
        if (out == null) {
            return
        }
        if (value == null) {
            out.nullValue()
        } else {
            out.value(value.value) // Flatten to string during serialization
        }
    }

    override fun read(input: com.google.gson.stream.JsonReader?): UpTransactionId {
        if (input == null) {
            throw IllegalStateException("input is null")
        }
        return UpTransactionId(input.nextString())
    }
}

class UpTransactionIdConverterFactory : Converter.Factory() {
    override fun stringConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, String>? {
        if (type == UpTransactionId::class.java) {
            return UpTransactionIdConverter()
        }
        return null
    }

    private class UpTransactionIdConverter : Converter<UpTransactionId, String> {
        override fun convert(value: UpTransactionId): String {
            return value.value // Assuming your UpTransactionId has an 'id' property
        }
    }
}


class ZonedDateTimeTypeAdapter : TypeAdapter<ZonedDateTime>() {

    override fun write(out: JsonWriter?, value: ZonedDateTime?) {
        if (out == null) {
            return
        }
        if (value == null) {
            out.nullValue()
            return
        }
        out.value(value.toString())
    }

    override fun read(input: JsonReader?): ZonedDateTime? {
        if (input == null) {
            throw IllegalStateException("input is null")
        }
        if (input.peek() == com.google.gson.stream.JsonToken.NULL) {
            input.nextNull()
            return null
        }

        val inputString = input.nextString()
        return ZonedDateTime.parse(inputString)
    }
}


