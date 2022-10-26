package com.example.hairhood.activities

import android.R.string
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hairhood.databinding.ActivityReservarPeluqueroBinding
import com.google.type.DateTime


class ReservarPeluquero : AppCompatActivity() {
    private lateinit var binding: ActivityReservarPeluqueroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservarPeluqueroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //binding.timePicker1.setMinDate(System.currentTimeMillis() - 1000);

        binding.erreserba.setOnClickListener(){
            val hour=binding.timePicker1.hour.toString()+" : "+binding.timePicker1.minute.toString()
            Toast.makeText(this, hour, Toast.LENGTH_SHORT).show()


            //val hora = binding.timePicker1.getCurrentHour() + ":" + binding.timePicker1.getCurrentMinute()
            //En caso de pulsar el  boton de + infor  nos redirige a reservar
            val intent= Intent(this, Pay::class.java)
            startActivity(intent)
            finish()
        }

    }
}