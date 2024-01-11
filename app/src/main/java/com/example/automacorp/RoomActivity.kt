package com.example.automacorp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.automacorp.model.RoomCommandDto
import com.example.automacorp.model.RoomDto
import com.example.automacorp.service.ApiServices
import com.example.automacorp.viewmodel.RoomViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoomActivity : BasicActivity(), OnRoomClickListener {
    private val viewModel: RoomViewModel by viewModels {
        RoomViewModel.factory
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val roomId = intent.getLongExtra(MainActivity.ROOM_ID_PARAM, 0)

        viewModel.findById(roomId).observe(this) { rooms ->
            populateScreen(rooms)
    }

//        lifecycleScope.launch(context = Dispatchers.IO) { // (1)
//            runCatching { ApiServices.roomsApiService.findById(roomId).execute() }
//                .onSuccess {
//                    withContext(context = Dispatchers.Main) { // (2)
//                        populateScreen(it.body())
//                    }
//                }
//                .onFailure {
//                    withContext(context = Dispatchers.Main) {
//                        it.printStackTrace()
//                        Toast.makeText(applicationContext, "Error on rooms list loading $it", Toast.LENGTH_LONG)
//                            .show()
//                    }
//                }
//        }
    }

    private fun populateScreen(room: RoomDto?) {
        val roomName = findViewById<EditText>(R.id.txt_room_name)
        roomName.setText(room?.name ?: "")

        val roomCurrentTemperature = findViewById<EditText>(R.id.txt_current_temperature)
        roomCurrentTemperature.setText(room?.currentTemperature?.toString() ?: "")

        val roomTargetTemperature = findViewById<EditText>(R.id.txt_target_temperature)
        roomTargetTemperature.setText(room?.targetTemperature?.toString() ?: "")

        findViewById<FloatingActionButton>(R.id.saveButton).setOnClickListener {
            saveRoom()
        }
    }

    override fun selectRoom(id: Long) {
        val intent = Intent(this, RoomActivity::class.java).putExtra(MainActivity.ROOM_ID_PARAM, id)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        findViewById<FloatingActionButton>(R.id.saveButton).setOnClickListener(null)
    }

    fun saveRoom() {
        val roomId = intent.getLongExtra(MainActivity.ROOM_ID_PARAM, -1)
        val roomDto = RoomCommandDto(
            name = findViewById<EditText>(R.id.txt_room_name).text.toString(),
            currentTemperature = findViewById<EditText>(R.id.txt_current_temperature).text.toString()
                .toDoubleOrNull(),
            targetTemperature = findViewById<EditText>(R.id.txt_target_temperature).text.toString()
                .toDoubleOrNull()
        )
        viewModel.save(roomId, roomDto).observe(this) {
            startActivity(Intent(applicationContext, RoomsActivity::class.java))
        }
//        lifecycleScope.launch(context = Dispatchers.IO) { // (1)
//            runCatching {
//                if (roomId == null || roomId == 0L) {
//                    ApiServices.roomsApiService.save(roomDto).execute()
//                } else {
//                    ApiServices.roomsApiService.updateRoom(roomId, roomDto).execute()
//                }
//            }
//                .onSuccess {
//                    withContext(context = Dispatchers.Main) {
//                        startActivity(Intent(applicationContext, RoomsActivity::class.java))
//                    }
//                }
//                .onFailure {
//                    withContext(context = Dispatchers.Main) {
//                        it.printStackTrace()
//                        Toast.makeText(applicationContext, "Error on room saving $it", Toast.LENGTH_LONG).show()  // (3)
//                    }
//                }
//        }
    }
}