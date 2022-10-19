package com.example.hairhood.activities

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hairhood.R
import com.example.hairhood.UserAdapter
import com.example.hairhood.databinding.ActivityAdminBinding
import com.example.hairhood.databinding.ActivityAdminUserBinding
import com.example.hairhood.fragments.Favorite
import com.example.hairhood.fragments.Profile
import com.example.hairhood.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AdminUserActivity : AppCompatActivity() {
    private lateinit var usuario: MutableMap<String, Any>
    private lateinit var binding: ActivityAdminUserBinding
    private val db = Firebase.firestore
    private lateinit var routine: CoroutineScope

    companion object{
        const val USER_INFO = "AdminUserActivity:userInfo"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAdminUserBinding.inflate(layoutInflater)
        val user:User=intent?.getParcelableExtra<User>(USER_INFO)?:throw IllegalStateException()
        setContentView(binding.root)
        binding.info.text=user.usuario
        binding.info.typeWrite(this, "Editar usuario: "+user.usuario , 333L)
        searchUserInfo(user)


        binding.btnRemoveClient.setOnClickListener {
            db.collection("clientes").document(user.usuario)
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(this, "Usuario eliminado correctamente", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@AdminUserActivity, AdminActivity::class.java))
                    finish()
                }
                .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error deleting document", e) }
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
        var tipo = "clientes"
        if (user.tipo == "P") tipo = "peluqueros"

        db.collection(tipo).whereEqualTo("usuario", user.usuario).get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
                if (task.isSuccessful) {
                    val userList: MutableList<User?> = ArrayList()
                    for (document in task.result) {
                        usuario = document.data
                        binding.nombreCliente.setText(usuario["nombre"].toString())
                        binding.usuarioCliente.setText(usuario["usuario"].toString())
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