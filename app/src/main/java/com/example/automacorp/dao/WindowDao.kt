package com.example.automacorp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.automacorp.model.Window

@Dao
interface WindowDao {
    @Query("select * from rwindow order by name")
    fun findAll(): List<Window>

    @Query("select * from rwindow where id = :windowId")
    fun findById(windowId: Long): Window

    @Insert
    suspend fun create(window: Window)

    @Update
    suspend fun update(window: Window): Int

    @Delete
    suspend fun delete(window: Window)

    @Query("delete from rwindow")
    suspend fun clearAll()

    @Query("select * from rwindow where name = :windowName")
    fun findByName(windowName: String): Window?

    @Query("select * from rwindow where room_id = :roomId")
    fun findByRoomId(roomId: Long): List<Window>
}