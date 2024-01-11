package com.example.automacorp.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.automacorp.model.EnumConverters
import com.example.automacorp.model.Window
import com.example.automacorp.model.Room

@Database(entities = [Window::class, Room::class], version = 1)
@TypeConverters(EnumConverters::class)
abstract class AutomacorpDatabase : RoomDatabase() {
    abstract fun windowDao(): WindowDao
    abstract fun roomDao(): RoomDao
}