package com.pratama.rvloadmore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pratama.rvloadmore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}


data class TextItem(
    val number: Int,
    val text: String
)