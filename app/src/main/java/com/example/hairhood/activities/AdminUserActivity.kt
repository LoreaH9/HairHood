package com.example.hairhood.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hairhood.databinding.ActivityAdminBinding
import com.example.hairhood.databinding.ActivityAdminUserBinding
import com.example.hairhood.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AdminUserActivity : AppCompatActivity() {
    private lateinit var usuario: MutableMap<String, Any>
    private lateinit var binding: ActivityAdminBinding
    private val db = Firebase.firestore


    companion object{
        const val USER_INFO = "AdminUserActivity:userInfo"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivityAdminUserBinding.inflate(layoutInflater)
        val user:User=intent?.getParcelableExtra<User>(USER_INFO)?:throw IllegalStateException()
        setContentView(binding.root)
        binding.info.text=user.usuario
        searchUserInfo(user)
        binding.usuarioPeluquero.text


    }

    private fun searchUserInfo(user:User){
        var tipo = "clientes"
        if (user.tipo == "P") tipo = "peluqueros"
        db.collection(tipo).whereEqualTo("usuario", user.usuario).get()
            .addOnSuccessListener { list ->
                list.forEach {
                    usuario = it.data
                }
            }
    }
}