package com.example.automacorp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.example.automacorp.model.Room

@Dao
interface RoomDao {

    @Query("SELECT * FROM rooms")
    fun findAll(): List<Room>

    @Query("SELECT * FROM rooms WHERE id = :id")
    fun findById(id: Long): Room?

    @Insert
    suspend fun save(room: Room)

    @Update
    suspend fun update(room: Room) : Int

    @Query("delete from rooms")
    suspend fun clearAll()
}
