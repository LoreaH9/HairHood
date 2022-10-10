package com.example.hairhood.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hairhood.R
import com.example.hairhood.databinding.ActivityRegisterBinding
import com.example.hairhood.databinding.ActivitySelectorPeluqueroBinding

class Selector_peluquero : AppCompatActivity() {
    private lateinit var binding: ActivitySelectorPeluqueroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectorPeluqueroBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}