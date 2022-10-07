package com.example.hairhood

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hairhood.databinding.ActivityRegisterBinding

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
        binding.singInCliente.setOnClickListener {

            if (TextUtils.isEmpty(binding.usuarioCliente.text.toString()) ||
                TextUtils.isEmpty(binding.passCliente.text.toString()) ||
                TextUtils.isEmpty(binding.emailCliente.text.toString()) ||
                TextUtils.isEmpty(binding.nombreCliente.text.toString()) ||
                TextUtils.isEmpty(binding.dniCliente.text.toString()) ||
                TextUtils.isEmpty(binding.numTlfCliente.text.toString()) ||
                TextUtils.isEmpty(binding.direccionCliente.text.toString())||
                TextUtils.isEmpty(binding.fechaCliente.text.toString())
            ) {
                Toast.makeText(this, "Por favor rellene todos los campos", Toast.LENGTH_SHORT)
                    .show();
            }
            val PassCliente: String = binding.passCliente.getText().toString()
            val ConfirmPassCliente: String = binding.passConfirmCliente.getText().toString()
            if (PassCliente != ConfirmPassCliente) {
                Toast.makeText(this, "No coinciden las contraseñas", Toast.LENGTH_SHORT).show()
            }else{
                val intentLogin =Intent(this, LoginActivity::class.java )
                startActivity(intentLogin)
            }
        }
        binding.singInPelu.setOnClickListener {

            if (TextUtils.isEmpty(binding.usuarioPeluquero.text.toString()) ||
                TextUtils.isEmpty(binding.passPeluquero.text.toString()) ||
                TextUtils.isEmpty(binding.emailPeluquero.text.toString()) ||
                TextUtils.isEmpty(binding.nombrePeluquero.text.toString()) ||
                TextUtils.isEmpty(binding.dniPeluquero.text.toString()) ||
                TextUtils.isEmpty(binding.numTlfPeluquero.text.toString()) ||
                TextUtils.isEmpty(binding.fechaPeluquero.text.toString())

            ) {
                Toast.makeText(this, "Por favor rellene todos los campos", Toast.LENGTH_SHORT)
                    .show();
            }
            val PassPeluquero: String = binding.passPeluquero.getText().toString()
            val ConfirmPassPeluquero: String = binding.passConfirmPeluquero.getText().toString()
            if (PassPeluquero != ConfirmPassPeluquero) {
                Toast.makeText(this, "No coinciden las contraseñas", Toast.LENGTH_SHORT).show()
            }else{
                val intentLogin =Intent(this, LoginActivity::class.java )
                startActivity(intentLogin)
            }
        }


        }

    }
