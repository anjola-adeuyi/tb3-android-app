package com.example.automacorp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import com.example.automacorp.viewmodel.WindowViewModel

class WindowActivity : BasicActivity() {
    private val viewModel: WindowViewModel by viewModels {
        WindowViewModel.factory
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_window)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val param = intent.getStringExtra(MainActivity.WINDOW_NAME_PARAM)
        val windowName = findViewById<EditText>(R.id.txt_window_name)
        val windowId = findViewById<EditText>(R.id.txt_window_id)
        if (param != null) {
            viewModel.findByName(param).observe(this) { window ->
                windowName.setText(window?.name)
                windowId.setText(window?.id?.toString())
            }
        }

//        windowName.setText("waiting...")
    }
}