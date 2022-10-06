package com.example.hairhood

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hairhood.databinding.ActivityLoginBinding



class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    lateinit var sharedPreferences: SharedPreferences
    var PREFS_KEY = "prefs"
    var EMAIL_KEY = "email"
    var PWD_KEY = "pwd"

    // on below line we are creating a
    // variable for email and password.
    var email = ""
    var pwd = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        email = sharedPreferences.getString(EMAIL_KEY, "").toString()
        pwd = sharedPreferences.getString(PWD_KEY, "").toString()

        binding.btnLogin.setOnClickListener {
            if (TextUtils.isEmpty(binding.user.text.toString()) || TextUtils.isEmpty(binding.password.text.toString())) {
                Toast.makeText(this, "Por favor introduzca un usuario y una contraseña", Toast.LENGTH_SHORT).show();
            } else {
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString(EMAIL_KEY, binding.user.text.toString())
                editor.putString(PWD_KEY, binding.password.text.toString())
                editor.apply()
                val i = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(i)
                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (email != "" || pwd != "") {
            val i = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}