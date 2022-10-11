package com.example.hairhood.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hairhood.databinding.ActivityAdminBinding
import com.example.hairhood.databinding.ActivityAdminUserBinding
import com.example.hairhood.model.User

class AdminUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    companion object{
        const val USER_INFO = "AdminUserActivity:userInfo"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivityAdminUserBinding.inflate(layoutInflater)
        val user:User=intent?.getParcelableExtra<User>(USER_INFO)?:throw IllegalStateException()

        setContentView(binding.root)
        binding.info.text=user.usuario
    }
}