package com.example.hairhood.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hairhood.databinding.ActivitySelectorPeluqueroBinding


class SelectorPeluquero : AppCompatActivity() {
    private lateinit var binding: ActivitySelectorPeluqueroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectorPeluqueroBinding.inflate(layoutInflater)
        setContentView(binding.root)

<<<<<<< Updated upstream:app/src/main/java/com/example/hairhood/activities/SelectorPeluquero.kt
        binding.infor.setOnClickListener() {
            val intent = Intent(this@SelectorPeluquero, ReservarPeluquero::class.java)
=======
        binding.infor.setOnClickListener(){
            //En caso de pulsar el  boton de + infor  nos redirige a reservar
            val intent= Intent(this, ReservarPeluquero::class.java)
>>>>>>> Stashed changes:app/src/main/java/com/example/hairhood/activities/Selector_peluquero.kt
            startActivity(intent)
        }

    }
}