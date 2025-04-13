package com.dicoding.core.data.source.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {
    private val gson = Gson()

    @TypeConverter
    fun fromPlatformList(platforms: List<String?>): String {
        return gson.toJson(platforms)
    }

    @TypeConverter
    fun toPlatformList(data: String): List<String?> {
        val listType = object : TypeToken<List<String?>>() {}.type
        return gson.fromJson(data, listType)
    }
}