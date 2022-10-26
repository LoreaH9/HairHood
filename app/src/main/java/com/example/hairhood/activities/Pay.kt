package com.example.hairhood.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.example.hairhood.R
import com.example.hairhood.databinding.ActivityPayBinding

class Pay : AppCompatActivity() {
    private lateinit var binding: ActivityPayBinding
    var errores = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Comprueba que el numero de la tarjeta tenga 16 digitos
        binding.editTextZenbakia.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                var txartelaZenbakia = binding.editTextZenbakia.text.toString()
                if (txartelaZenbakia.length != 16) {
                    binding.editTextZenbakia.error = getString(R.string.txartelaError)
                    errores = true
                } else {
                    errores = false
                }
            }
        }
        //Comprobar que el campo del mes tenga dos digitos
        binding.editTextMes.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                var mes = binding.editTextMes.text.toString()
                if (mes.length != 2) {
                    binding.editTextMes.error = getString(R.string.mesError)
                    errores = true
                } else {
                    errores = false
                }
            }
        }
        //Comprobar que el campo del año tiene 2 digitos
        binding.editTextAno.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                var ano = binding.editTextAno.text.toString()
                if (ano.length != 2) {
                    binding.editTextAno.error = getString(R.string.anoError)
                    errores = true
                } else {
                    errores = false
                }
            }
        }
        //Comprobar que el cvv tiene 3 digitos
        binding.editextZenbakiaCvv.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                var cvv = binding.editextZenbakiaCvv.text.toString()
                if (cvv.length != 3) {
                    binding.editextZenbakiaCvv.error = getString(R.string.cvvError)
                    errores = true
                } else {
                    errores = false
                }
            }
        }

        binding.btnOrdaindu.setOnClickListener() {
            //Comprueba que los campos no esten vacios
            if (TextUtils.isEmpty(binding.editTextZenbakia.text.toString()) ||
                TextUtils.isEmpty(binding.editTextMes.text.toString()) ||
                TextUtils.isEmpty(binding.editTextAno.text.toString()) ||
                TextUtils.isEmpty(binding.editextZenbakiaCvv.text.toString())
            ) {
                //Enseña un mensaje si no estan rellenados todos los campos
                Toast.makeText(this, R.string.vacio, Toast.LENGTH_SHORT).show();
            }else if (!errores){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

            }

        }
    }
}