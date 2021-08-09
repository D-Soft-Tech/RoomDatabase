package com.example.roomdatabase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.roomdatabase.data.AppViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initializing The view model
        viewModel = ViewModelProvider(this).get(AppViewModel::class.java)

        // Setup action bar with nav controller
        setupActionBarWithNavController(findNavController(R.id.navHost))
    }
}
