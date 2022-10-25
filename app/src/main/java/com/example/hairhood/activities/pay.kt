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
import com.example.hairhood.fragments.Chat
import com.example.hairhood.fragments.Favorite
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
                Toast.makeText(this, R.string.vacio, Toast.LENGTH_SHORT).show();
            } else {
                /*if(binding.editTextZenbakia.text.toString().length>=16){
                    var i=0
                    var numero=false

                    while(i<binding.editTextZenbakia.text.toString().length || (!numero)){
                        val g = binding.editTextZenbakia.text.toString()
                        val c = g.get(index = i)
                        if( c in '0'..'9'){
                            numero=true
                        }
                        i++
                        Toast.makeText(this,"Bien", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(this,"Mal", Toast.LENGTH_SHORT).show();
                }*/


                //Pulsando en pagar nos lleva de nuevo al mapa
                val intent= Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}