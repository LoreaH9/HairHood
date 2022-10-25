package com.example.hairhood.activities

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.hairhood.databinding.ActivityAdminUserBinding
import com.example.hairhood.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.*

class AdminUserActivity : AppCompatActivity() {
    private lateinit var usuario: MutableMap<String, Any>
    private lateinit var binding: ActivityAdminUserBinding
    private val db = Firebase.firestore
    private lateinit var routine: CoroutineScope
    private val storage = Firebase.storage
    private var storageReference = storage.reference

    companion object{
        const val USER_INFO = "AdminUserActivity:userInfo"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAdminUserBinding.inflate(layoutInflater)
        val user:User=intent?.getParcelableExtra<User>(USER_INFO)?:throw IllegalStateException()
        setContentView(binding.root)

        binding.info.typeWrite(this, "Editar usuario: "+user.usuario , 300L)
        searchUserInfo(user)

        binding.btnRemoveClient.setOnClickListener {
            db.collection("clientes").document(user.usuario).delete().addOnSuccessListener {Toast.makeText(this, "Usuario eliminado correctamente", Toast.LENGTH_SHORT).show() }
            val ref = storageReference.child("clientes/$usuario.jpg")
            ref
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(this, "Foto eliminada correctamente", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@AdminUserActivity, AdminActivity::class.java))
                    finish()
                }
                .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error deleting document", e) }
        }
        binding.saveChangesUser.setOnClickListener {
            val datoC = hashMapOf(
                "nombre" to binding.nombreCliente.text.toString(),
                "dni" to binding.dniCliente.text.toString(),
                "numTelefono" to binding.numTlfCliente.text.toString().toInt(),
                "fechaNacimiento" to binding.fechaCliente.text.toString(),
                "direccion" to binding.direccionCliente.text.toString(),
                "email" to binding.emailCliente.text.toString()
            )
            db.collection("clientes").document(user.usuario).update(datoC as Map<String, Any>)
            Toast.makeText(this,"Usuario actualizado correctamente",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@AdminUserActivity, AdminActivity::class.java))
        }
    }

    private fun showLoading() {
        binding.progressBarUser.visibility = View.VISIBLE
        binding.ScrollUsuario.visibility= View.GONE;
    }

    private fun hideLoading() {
        binding.progressBarUser.visibility = View.GONE
        binding.ScrollUsuario.visibility= View.VISIBLE;
    }

    private fun searchUserInfo(user:User){
        showLoading()

        db.collection( "clientes").whereEqualTo("usuario", user.usuario).get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        usuario = document.data
                        binding.nombreCliente.setText(usuario["nombre"].toString())
                        binding.dniCliente.setText(usuario["dni"].toString())
                        binding.numTlfCliente.setText(usuario["numTelefono"].toString())
                        binding.fechaCliente.setText(usuario["fechaNacimiento"].toString())
                        binding.direccionCliente.setText(usuario["direccion"].toString())
                        binding.emailCliente.setText(usuario["email"].toString())
                        binding.passCliente.setText(usuario["contraseña"].toString())
                    }
                    hideLoading()
                }
            })

    }

    fun TextView.typeWrite(lifecycleOwner: LifecycleOwner, text: String, intervalMs: Long) {
        this@typeWrite.text = ""
        if(::routine.isInitialized) routine.cancel()

        lifecycleOwner.lifecycleScope.launch {
            routine = this
            repeat(text.length) {
                delay(intervalMs)
                this@typeWrite.text = text.take(it + 1)
            }
        }
    }
}