package com.example.hairhood.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.hairhood.R
import com.example.hairhood.databinding.ActivityPayBinding
import com.example.hairhood.databinding.ActivityReservarPeluqueroBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class pay : AppCompatActivity() {
    private lateinit var binding: ActivityPayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOrdaindu.setOnClickListener(){
            if (TextUtils.isEmpty(binding.editTextZenbakia.text.toString()) ||
                TextUtils.isEmpty(binding.editTextMes.text.toString()) ||
                TextUtils.isEmpty(binding.editTextAno.text.toString()) ||
                TextUtils.isEmpty(binding.editextZenbakiaCvv.text.toString())
            ) {
                Toast.makeText(this,"@String/vacio", Toast.LENGTH_SHORT).show();
            } else {
                val passPeluquero: String =binding.passPeluquero.getText().toString()
                val confirmPassPeluquero: String =binding.passConfirmPeluquero.getText().toString()

                if (passPeluquero != confirmPassPeluquero) {
                    Toast.makeText(this,"No coinciden las contraseñas", Toast.LENGTH_SHORT).show()
                } else {
                    lifecycleScope.launch(Dispatchers.IO){
                        verifyUser(binding.usuarioPeluquero.text.toString(), "p",passPeluquero)
                    }
                }
            }





            //Pulsando en pagar nos lleva de nuevo al mapa
            val intent= Intent(this, com.example.hairhood.fragments.Map::class.java)
            startActivity(intent)
            finish()
        }
    }
}