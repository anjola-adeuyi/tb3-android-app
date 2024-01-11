package com.example.automacorp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.liveData
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.automacorp.AutomacorpApplication
import com.example.automacorp.dao.WindowDao
import com.example.automacorp.model.Window
import com.example.automacorp.model.WindowCommandDto
import com.example.automacorp.model.WindowDto
import kotlinx.coroutines.Dispatchers

enum class State { ONLINE, OFFLINE }

class WindowViewModel(private val windowDao: WindowDao) : ViewModel() { // (1)

    fun findById(windowId: Long): LiveData<WindowDto> = // (2)
        liveData(Dispatchers.IO) { // (3)
            emit(windowDao.findById(windowId).toDto()) // (4)
        }

    fun findByName(windowName: String): LiveData<WindowDto?> = // (2)
        liveData(Dispatchers.IO) { // (3)
            emit(windowDao.findByName(windowName)?.toDto()) // (4)
        }

//    fun save(windowId: Long, command: WindowCommandDto): LiveData<WindowDto> = // (2)
//        liveData(Dispatchers.IO) { // (3)
//            val window = Window(
//                id = windowId,
//                name = command.name
//            )
//            if (windowId == 0L) {
//                windowDao.create(window)
//            } else {
//                windowDao.update(window)
//            }
//            emit(window.toDto()) // (4)
//        }

    companion object {
        val factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    // Load the Dao from the Application object
                    val windowDao = (extras[APPLICATION_KEY] as AutomacorpApplication)
                        .database
                        .windowDao()
                    return WindowViewModel(windowDao) as T
                }
            }
    }

}