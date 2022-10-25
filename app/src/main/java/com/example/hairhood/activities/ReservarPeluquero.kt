package com.example.hairhood.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hairhood.databinding.ActivityReservarPeluqueroBinding

class ReservarPeluquero : AppCompatActivity() {
    private lateinit var binding: ActivityReservarPeluqueroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservarPeluqueroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.erreserba.setOnClickListener(){
            //En caso de pulsar el  boton de + infor  nos redirige a reservar
            val intent= Intent(this, pay::class.java)
            startActivity(intent)
            finish()
        }

    }
}