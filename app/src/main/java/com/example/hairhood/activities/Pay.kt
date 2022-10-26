package com.example.hairhood.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.hairhood.R
import com.example.hairhood.databinding.ActivityPayBinding

class Pay : AppCompatActivity() {
    private lateinit var binding: ActivityPayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOrdaindu.setOnClickListener(){
            //Comprueba que los campos no esten vacios
            if (TextUtils.isEmpty(binding.editTextZenbakia.text.toString()) ||
                TextUtils.isEmpty(binding.editTextMes.text.toString()) ||
                TextUtils.isEmpty(binding.editTextAno.text.toString()) ||
                TextUtils.isEmpty(binding.editextZenbakiaCvv.text.toString())
            ) {
                //Enseña un mensaje si no estan rellenados todos los campos
                Toast.makeText(this, R.string.vacio, Toast.LENGTH_SHORT).show();
            } else{
                //Comprueba que el numero de la tarjeta tenga 16 digitos
                if(binding.editTextZenbakia.text.toString().length==16){
                    var i=0
                    var numero=false

                    while(i<binding.editTextZenbakia.text.toString().length || (!numero)){
                        val g = binding.editTextZenbakia.text.toString()
                        val c = g.get(index = i)
                        if( c in '0'..'9'){
                            numero=true
                        }
                        i++
                    }
                    //Comprobar que el campo del mes tenga dos digitos
                    if(binding.editTextMes.text.toString().length==2){
                        var i=0
                        var numero=false

                        while(i<binding.editTextZenbakia.text.toString().length || (!numero)) {
                            val g = binding.editTextZenbakia.text.toString()
                            val c = g.get(index = i)
                            if (c in '0'..'9') {
                                numero = true
                            }
                            i++
                        }
                        //Comprobar que el campo del año tiene 2 digitos
                        if(binding.editTextAno.text.toString().length==2){
                            var i=0
                            var numero=false

                            while(i<binding.editTextZenbakia.text.toString().length || (!numero)){
                                val g = binding.editTextZenbakia.text.toString()
                                val c = g.get(index = i)
                                if( c in '0'..'9'){
                                    numero=true
                                }
                                i++
                            }
                            //Comprobar que el cvv tiene 3 digitos
                            if(binding.editextZenbakiaCvv.text.toString().length==3){
                                var i=0
                                var numero=false

                                while(i<binding.editTextZenbakia.text.toString().length || (!numero)){
                                    val g = binding.editTextZenbakia.text.toString()
                                    val c = g.get(index = i)
                                    if( c in '0'..'9'){
                                        numero=true
                                    }
                                    i++

                                    //Pulsando en pagar nos lleva de nuevo al mapa
                                    val intent= Intent(this, com.example.hairhood.fragments.Map::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            } else{
                                Toast.makeText(this,"Falta algun numero en el CVV", Toast.LENGTH_SHORT).show();
                            }
                        } else{
                            //Enseña un mensaje si al año no tiene 2 numeros
                            Toast.makeText(this, "Falta algun numero en el AÑO", Toast.LENGTH_SHORT).show();
                        }
                    } else{
                        //Enseña un mensaje si el mes no tiene 2 numeros
                        Toast.makeText(this, "Falta algun numero en el MES", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    //Enseña un mensaje si el numero de la tarjeta no tiene 16 digitos
                    Toast.makeText(this, "Falta algun numero en la TARJETA", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}