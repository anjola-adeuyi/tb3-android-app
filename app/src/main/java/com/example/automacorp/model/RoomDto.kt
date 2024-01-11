package com.example.automacorp.model

data class RoomDto(
    val id: Long,
    val name: String,
    val currentTemperature: Double?,
    val targetTemperature: Double?,
    val windows: List<WindowDto>
)

data class RoomCommandDto(
    val name: String,
    val currentTemperature: Double?,
    val targetTemperature: Double?,
    val floor: Int = 1,
    // Set to the default building ID (useful when you have not created screens to manage buildings)
    val buildingId: Long = -10
)
