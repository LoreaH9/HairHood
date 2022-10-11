package com.example.hairhood.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hairhood.databinding.ActivitySelectorPeluqueroBinding


class Selector_peluquero : AppCompatActivity() {
    private lateinit var binding: ActivitySelectorPeluqueroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectorPeluqueroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.infor.setOnClickListener(){
            //En caso de pulsar el  boton de + infor  nos redirige a reservar
            val intent= Intent(this, Reservar_peluquero::class.java)
            startActivity(intent)
            finish()
        }
    }




}