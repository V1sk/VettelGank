package com.cjw.vettelgank.data.api

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class GankDateDeserializer(vararg dateFormatPatterns: String) : JsonDeserializer<Date> {

    private val dateFormats: MutableList<SimpleDateFormat> = arrayListOf()

    init {
        for (item in dateFormatPatterns) {
            dateFormats.add(SimpleDateFormat(item, Locale.getDefault()))
        }
    }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Date? {
        if (json == null) {
            return null
        }

        for (dateFormat in dateFormats) {
            try {
                return dateFormat.parse(json.asString)
            } catch (e: ParseException) {

            }
        }
        return null
    }

}