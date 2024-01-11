package com.example.automacorp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.automacorp.AutomacorpApplication
import com.example.automacorp.dao.RoomDao
import com.example.automacorp.dao.WindowDao
import com.example.automacorp.model.Room
import com.example.automacorp.model.RoomCommandDto
import com.example.automacorp.model.RoomDto
import com.example.automacorp.model.Window
import com.example.automacorp.service.ApiServices
import kotlinx.coroutines.Dispatchers

class RoomViewModel(private val roomDao: RoomDao, private val windowDao: WindowDao): ViewModel() {
    companion object {
        val factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    val roomDao = (extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AutomacorpApplication)
                        .database
                        .roomDao()
                    val windowDao = (extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AutomacorpApplication)
                        .database
                        .windowDao()
                    return RoomViewModel(roomDao, windowDao) as T
                }
            }
    }

    val networkState: MutableLiveData<State> by lazy {
        MutableLiveData<State>().also { it.postValue(State.ONLINE) }
    }

    fun findAll(): LiveData<List<RoomDto>> =
        liveData(Dispatchers.IO) {
            runCatching {
                ApiServices.roomsApiService.findAll().execute()
            }.onSuccess {
                it.body()
                    ?.also { rooms ->
                        roomDao.clearAll()
                        windowDao.clearAll()
                        rooms.onEach { room ->
                            roomDao.save(
                                Room(
                                    id = room.id,
                                    name = room.name,
                                    currentTemperature = room.currentTemperature,
                                    targetTemperature = room.targetTemperature
                                )
                            )
                            room.windows.onEach {
                                windowDao.create(
                                    Window(
                                        id = it.id,
                                        name = it.name,
                                        roomId = room.id,
                                        roomName = room.name,
                                        windowStatus = it.windowStatus
                                    )
                                )
                            }
                        }
                        emit(rooms)
                    }
                    ?: emit(emptyList())
            }.onFailure {
                val windows = windowDao.findAll()
                val rooms = roomDao.findAll().map { room ->
                    room.toDto(
                        windows.filter { it.roomId == room.id }.map { it.toDto() }
                    ) }
                emit(rooms)
            }
        }

    fun findById(roomId: Long): LiveData<RoomDto?> =
        liveData(Dispatchers.IO) {
            runCatching {
                ApiServices.roomsApiService.findById(roomId).execute().body()!!
            }.onSuccess {
                networkState.postValue(State.ONLINE)
                emit(it)
            }.onFailure {
                networkState.postValue(State.OFFLINE)
                val room = roomDao.findById(roomId)
                    val windows = windowDao.findByRoomId(roomId)
                emit(room?.toDto(windows.map{window ->  window.toDto()}))
            }
        }

    fun save(roomId: Long, room: RoomCommandDto): LiveData<RoomDto?> =
        liveData(Dispatchers.IO) {
            runCatching {
                if (roomId == 0L) {
                    ApiServices.roomsApiService.save(room).execute().body()
                } else {
                    ApiServices.roomsApiService.updateRoom(roomId, room).execute().body()
                }
            }.onSuccess {
                emit(it)
            }.onFailure {
                emit(null)
            }
        }
}
