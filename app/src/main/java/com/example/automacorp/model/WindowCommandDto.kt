package com.example.automacorp.model
data class WindowCommandDto(
    val id: Long,
    val name: String,
    val roomId: Long,
    val windowStatus: WindowStatus
)
