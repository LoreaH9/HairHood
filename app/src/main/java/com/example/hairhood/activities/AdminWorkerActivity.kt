package com.example.hairhood.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hairhood.R
import com.example.hairhood.databinding.ActivityAdminBinding
import com.example.hairhood.databinding.ActivityAdminUserBinding
import com.example.hairhood.databinding.ActivityAdminWorkerBinding
import com.example.hairhood.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AdminWorkerActivity : AppCompatActivity() {
    private lateinit var usuario: MutableMap<String, Any>
    private lateinit var binding: ActivityAdminBinding
    private val db = Firebase.firestore

    companion object{
        const val USER_INFO ="AdminWorkerActivity:info"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding= ActivityAdminWorkerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user: User =intent?.getParcelableExtra<User>(AdminWorkerActivity.USER_INFO)?:throw IllegalStateException()
        binding.info.text=user.usuario

    }
}