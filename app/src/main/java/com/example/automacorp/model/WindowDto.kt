package com.example.automacorp.model
enum class WindowStatus { OPENED, CLOSED}

data class WindowDto(
    val id: Long,
    val name: String,
    val roomName: String,
    val roomId: Long,
    val windowStatus: WindowStatus
)
