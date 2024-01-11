package com.example.automacorp

import android.app.Application
import androidx.room.Room
import com.example.automacorp.dao.AutomacorpDatabase

class AutomacorpApplication : Application() {
    val database: AutomacorpDatabase by lazy {
        Room.databaseBuilder(this, AutomacorpDatabase::class.java, "automacorpdatabase")
            .build()
    }
}
