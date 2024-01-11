package com.example.automacorp.model

import androidx.room.TypeConverter

class EnumConverters {

    // A first method to convert enum in string when the data will be stored in the database
    @TypeConverter
    fun fromWindowStatus(value: WindowStatus?): String? {
        return value?.toString()
    }

    // A second one to do the inverse operation
    @TypeConverter
    fun toWindowStatus(value: String?): WindowStatus? {
        return value?.let { WindowStatus.valueOf(it) }
    }

}