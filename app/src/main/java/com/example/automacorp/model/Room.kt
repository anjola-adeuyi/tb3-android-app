package com.example.automacorp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "rooms")
data class Room(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "room_name") val name: String,
    @ColumnInfo(name = "current_temperature") val currentTemperature: Double?,
    @ColumnInfo(name = "target_temperature") val targetTemperature: Double?,
) {
//    @Ignore
//    val windows: MutableList<Window> = mutableListOf()

    fun toDto(windowsLoaded:List<WindowDto>) : RoomDto {
        return RoomDto(
            id=  id,
            name = name,
            currentTemperature = currentTemperature,
            targetTemperature = targetTemperature,
            windows = windowsLoaded
        )
    }
}