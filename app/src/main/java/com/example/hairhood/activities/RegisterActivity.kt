package com.example.hairhood.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hairhood.R
import com.example.hairhood.databinding.ActivityRegisterBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.security.MessageDigest

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db=FirebaseFirestore.getInstance()
//link para intentar : https://es.acervolima.com/como-crear-y-agregar-datos-a-firebase-firestore-en-android/

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
            }else{
                guardarDatosCliente(db)
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
            }else{
                guardarDatosPeluquero(db)
            }

            val passPeluquero: String = binding.passPeluquero.getText().toString()
            val confirmPassPeluquero: String = binding.passConfirmPeluquero.getText().toString()
            if (passPeluquero != confirmPassPeluquero) {
                Toast.makeText(this, "No coinciden las contraseñas", Toast.LENGTH_SHORT).show()
            }else{

                val intentLogin =Intent(this, LoginActivity::class.java )
                startActivity(intentLogin)
            }
        }
    }

fun guardarDatosCliente(db: FirebaseFirestore) {

    val datoC = hashMapOf(
        "usuario" to binding.usuarioCliente.text.toString(),
        "nombre" to binding.nombreCliente.text.toString(),
        "dni" to binding.dniCliente.text.toString(),
        "numTelefono" to binding.numTlfCliente.text.toString().toInt(),
        "fechaNacimiento" to binding.fechaCliente.text.toString(),
        "direccion" to binding.direccionCliente.text.toString(),
        "email" to binding.emailCliente.text.toString(),
        "contraseña" to binding.passCliente.text.toString())
    db.collection("clientes").document(binding.usuarioCliente.text.toString())
        .set(datoC)
        .addOnSuccessListener { resultado ->
            val intentLogin =Intent(this, MainActivity::class.java )
            startActivity(intentLogin)
        }
        .addOnFailureListener { Exception ->
            Toast.makeText(this, "Error al crear el usuario", Toast.LENGTH_SHORT).show() }
}
    fun guardarDatosPeluquero(db: FirebaseFirestore) {

        val datoP = hashMapOf(
            "usuario" to binding.usuarioPeluquero.text.toString(),
            "nombre" to binding.nombrePeluquero.text.toString(),
            "dni" to binding.dniPeluquero.text.toString(),
            "numTelefono" to binding.numTlfPeluquero.text.toString().toInt(),
            "email" to binding.emailPeluquero.text.toString(),
            "contraseña" to binding.passPeluquero.text.toString())
        db.collection("peluqueros").document(binding.usuarioPeluquero.text.toString())
            .set(datoP)
            .addOnSuccessListener { resultado ->
                val intentLogin =Intent(this, MainActivity::class.java )
                startActivity(intentLogin)
            }
            .addOnFailureListener { Exception ->
                Toast.makeText(this, "Error al crear el usuario", Toast.LENGTH_SHORT).show() }
    }


}
