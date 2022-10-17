package com.example.hairhood.activities

//https://www.youtube.com/watch?v=QUC9BILIbJ8 Video para subir archivos

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.hairhood.R
import com.example.hairhood.databinding.ActivityRegisterBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.security.MessageDigest

@Suppress("DEPRECATION")
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    lateinit var sharedPreferences: SharedPreferences
    val db=FirebaseFirestore.getInstance()
    private val File=1
    private val database = Firebase.database
    val myRef=database.getReference("Clientes")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        user = sharedPreferences.getString(USER_KEY, "").toString()

        val db=FirebaseFirestore.getInstance()

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
                Toast.makeText(this, "Por favor rellene todos los campos", Toast.LENGTH_SHORT).show();
            }else{
                val passCliente: String = binding.passCliente.getText().toString()
                val confirmPassCliente: String = binding.passConfirmCliente.getText().toString()
                if (passCliente != confirmPassCliente) {
                    Toast.makeText(this, "No coinciden las contraseñas", Toast.LENGTH_SHORT).show()
                }else {
                    lifecycleScope.launch(Dispatchers.IO){
                        verifyUser(binding.usuarioCliente.text.toString(), "p",passCliente)
                    }
                }
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
                Toast.makeText(this,"Por favor rellene todos los campos",Toast.LENGTH_SHORT).show();
            } else {
                val passPeluquero: String =binding.passPeluquero.getText().toString()
                val confirmPassPeluquero: String =binding.passConfirmPeluquero.getText().toString()

                if (passPeluquero != confirmPassPeluquero) {
                    Toast.makeText(this,"No coinciden las contraseñas",Toast.LENGTH_SHORT).show()
                } else {
                    lifecycleScope.launch(Dispatchers.IO){
                        verifyUser(binding.usuarioPeluquero.text.toString(), "p",passPeluquero)
                    }
                }
            }
        }

        binding.btnFotoCliente.setOnClickListener{
            subir_Archivo()
        }
    }

    private suspend fun verifyUser(userName: String, s: String, pass: String) {
        var existe = false

        var existeCli = db.collection("clientes").whereEqualTo("usuario",userName).get().await()
        var existeUsu = db.collection("clientes").whereEqualTo("usuario",userName).get().await()

        if (existeCli.isEmpty && existeUsu.isEmpty) {
            if(s=="c"){guardarDatosCliente(db)}else{guardarDatosPeluquero(db)}
            saveChanges(binding.usuarioCliente.toString(),pass)
            startActivity(Intent(this, LoginActivity::class.java))
        }
        /*db.collection("clientes").whereEqualTo("usuario",userName).get()
            .addOnSuccessListener { list ->
            for (usuario in list) {existe = true
                break
            }
            db.collection("peluqueros").whereEqualTo("usuario",userName).get()
                .addOnSuccessListener { list ->
                for (usuario in list) {existe = true
                    break}
                if (!existe) {
                    if(s=="c"){guardarDatosCliente(db)}else{guardarDatosPeluquero(db)}
                    saveChanges(binding.usuarioCliente.toString(),pass)
                    startActivity(Intent(this, LoginActivity::class.java))
                }else{
                    Toast.makeText(this,"Nombre de Usuario existente",Toast.LENGTH_LONG).show()
                }
            }
        }*/
    }


    fun subir_Archivo(){
        val intent=Intent(Intent.ACTION_GET_CONTENT)
        intent.type="*/*"
        startActivityForResult(intent,File)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == File){
            if (resultCode == RESULT_OK){
                val FileUri = data!!.data
                val folder:StorageReference=FirebaseStorage.getInstance().getReference().child("Clientes")

                val file_name: StorageReference = folder.child("file"+ FileUri!!.lastPathSegment)
                file_name.getDownloadUrl().addOnSuccessListener{uri->
                    val hashMap = HashMap<String,String>()
                    hashMap["link"] =java.lang.String.valueOf(uri)
                    myRef.setValue(hashMap)
                    Log.d("Mensaje", "Se subió correctamente")
                }
            }
        }
    }


    private fun guardarDatosCliente(db: FirebaseFirestore) {
        val pass = hashPassword(binding.passCliente.text.toString())
        val datoC = hashMapOf(
            "usuario" to binding.usuarioCliente.text.toString(),
            "nombre" to binding.nombreCliente.text.toString(),
            "dni" to binding.dniCliente.text.toString(),
            "numTelefono" to binding.numTlfCliente.text.toString().toInt(),
            "fechaNacimiento" to binding.fechaCliente.text.toString(),
            "direccion" to binding.direccionCliente.text.toString(),
            "email" to binding.emailCliente.text.toString(),
            "contraseña" to pass
        )
        db.collection("clientes")
            .document(binding.usuarioCliente.text.toString())
            .set(datoC)
            .addOnSuccessListener {
                startActivity(Intent(this, MainActivity::class.java))
            }
            .addOnFailureListener {
                Toast.makeText(this,"Error al crear el usuario",Toast.LENGTH_SHORT).show()
            }
    }
    private fun hashPassword(psswd: String): String {
        val digest = MessageDigest.getInstance("SHA-1")
        val result = digest.digest(psswd.toByteArray(Charsets.UTF_8))
        val sb = StringBuilder()
        for (b in result) {sb.append(String.format("%02X", b))}
        return sb.toString()
    }

    private fun guardarDatosPeluquero(db: FirebaseFirestore) {
        val pass = hashPassword(binding.passPeluquero.text.toString())

        val datoP = hashMapOf(
            "usuario" to binding.usuarioPeluquero.text.toString(),
            "nombre" to binding.nombrePeluquero.text.toString(),
            "dni" to binding.dniPeluquero.text.toString(),
            "numTelefono" to binding.numTlfPeluquero.text.toString()
                .toInt(),
            "email" to binding.emailPeluquero.text.toString(),
            "contraseña" to pass
        )
        db.collection("peluqueros")
            .document(binding.usuarioPeluquero.text.toString())
            .set(datoP)
            .addOnSuccessListener { resultado ->
                val intentLogin = Intent(this, MainActivity::class.java)
                startActivity(intentLogin)
            }
            .addOnFailureListener { Exception ->
                Toast.makeText(
                    this,
                    "Error al crear el usuario",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun saveChanges(user: String, PassCliente: String) {
        val psswd = hashPassword(PassCliente)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(USER_KEY, user)
        editor.putString(PWD_KEY, psswd)
        editor.apply()

    }
}