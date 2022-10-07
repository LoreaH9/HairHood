package com.example.hairhood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.hairhood.databinding.ActivityLoginBinding
import com.example.hairhood.databinding.ActivityRegisterBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.singUpUser.setOnClickListener {
            binding.singUpUser.background = resources.getDrawable(R.drawable.switch_trcks,null)
            binding.singUpUser.setTextColor(resources.getColor(R.color.textColor,null))
            binding.singUpWorker.background = null
            binding.ScrollUsuario.visibility = View.VISIBLE
            binding.ScrollPeluquero.visibility = View.GONE
            binding.singUpWorker.setTextColor(resources.getColor(R.color.pinkColor,null))
        }

        binding.singUpWorker.setOnClickListener {
            binding.singUpUser.background = null
            binding.singUpUser.setTextColor(resources.getColor(R.color.pinkColor,null))
            binding.singUpWorker.background = resources.getDrawable(R.drawable.switch_trcks,null)
            binding.ScrollUsuario.visibility = View.GONE
            binding.ScrollPeluquero.visibility = View.VISIBLE
            binding.singUpWorker.setTextColor(resources.getColor(R.color.textColor,null))
        }
        binding.singInCliente.setOnClickListener{

            if (TextUtils.isEmpty(binding.usuarioCliente.text.toString()) ||
                TextUtils.isEmpty(binding.passCliente.text.toString()) ||
                TextUtils.isEmpty(binding.emailCliente.text.toString()) ||
                TextUtils.isEmpty(binding.nombreCliente.text.toString()) ||
                TextUtils.isEmpty(binding.dniCliente.text.toString()) ||
                TextUtils.isEmpty(binding.numTlfCliente.text.toString()) ||
                TextUtils.isEmpty(binding.direccionCliente.text.toString())) {
                    Toast.makeText(this, "Por favor rellene todos los campos", Toast.LENGTH_SHORT).show();
                }else if (!binding.passCliente.equals(binding.passConfirmCliente)) {
                    Toast.makeText(this, "No coinciden las contraseñas", Toast.LENGTH_SHORT).show()
            }

           // val intentMapa =Intent(this, Map::class.java)
            //startActivity(intentMapa)
        }
        //binding.singInPelu.setOnClickListener{
          //  val intentMapa =Intent(this, Map::class.java)
            //startActivity(intentMapa)
        //}
    }
}