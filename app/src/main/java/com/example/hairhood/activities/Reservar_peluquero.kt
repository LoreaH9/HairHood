package com.example.hairhood.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hairhood.databinding.ActivityReservarPeluqueroBinding

class Reservar_peluquero : AppCompatActivity() {
    private lateinit var binding: ActivityReservarPeluqueroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservarPeluqueroBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}