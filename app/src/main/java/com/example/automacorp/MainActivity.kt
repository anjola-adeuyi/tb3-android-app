package com.example.automacorp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText

class MainActivity : BasicActivity() {

    companion object {
        const val WINDOW_NAME_PARAM = "com.automacorp.windowname.attribute"
        const val ROOM_ID_PARAM = "1"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun openWindow(view: View) {
        // Extract value filled in editext identified with txt_window_name id
        val windowName = findViewById<EditText>(R.id.txt_window_name).text.toString()

        val intent = Intent(this, WindowActivity::class.java).apply {
            putExtra(WINDOW_NAME_PARAM, windowName)
        }
        startActivity(intent)
    }

    fun openRoom(view: View) {

        val intent = Intent(this, RoomActivity::class.java).apply {
            putExtra(ROOM_ID_PARAM, 1L)
        }
        startActivity(intent)
    }
}