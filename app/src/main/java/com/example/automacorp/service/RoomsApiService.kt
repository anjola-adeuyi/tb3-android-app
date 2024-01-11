package com.example.automacorp.service

import com.example.automacorp.model.RoomCommandDto
import com.example.automacorp.model.RoomDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RoomsApiService {
    @GET("rooms")
    fun findAll(): Call<List<RoomDto>>

    @GET("rooms/{id}")
    fun findById(@Path("id") id: Long): Call<RoomDto>

    @PUT("rooms/{id}")
    fun updateRoom(@Path("id") id: Long, @Body room: RoomCommandDto): Call<RoomDto>

    @POST("rooms")
    fun save(@Body roomDto: RoomCommandDto): Call<RoomDto>
}