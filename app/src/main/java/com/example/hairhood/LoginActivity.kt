package com.example.hairhood

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hairhood.databinding.ActivityLoginBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    lateinit var sharedPreferences: SharedPreferences
    var PREFS_KEY = "prefs"
    var USER_KEY = "email"
    var PWD_KEY = "pwd"

    // on below line we are creating a
    // variable for email and password.
    var email = ""
    var pwd = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = Firebase.firestore
        sharedPreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        email = sharedPreferences.getString(USER_KEY, "").toString()
        pwd = sharedPreferences.getString(PWD_KEY, "").toString()

        binding.btnLogin.setOnClickListener {
            if (TextUtils.isEmpty(binding.user.text.toString()) || TextUtils.isEmpty(binding.password.text.toString())) {
                Toast.makeText(this, "Por favor introduzca el usuario y la contraseña", Toast.LENGTH_SHORT).show();
            } else {
                //Mira si es un usuario
                db.collection("clientes")
                    .get()
                    .addOnSuccessListener { list ->
                        list.forEach {
                            //binding.textView.text = binding.textView.text.toString() + "${it.id}, ${it.data["nombre"]}, ${it.data["ciudad"]}\n"
                            if (binding.user.text.toString() == it.data["usuario"] && binding.password.text.toString() == it.data["contraseña"]) {
                                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                                editor.putString(USER_KEY, binding.user.text.toString())
                                editor.putString(PWD_KEY, binding.password.text.toString())
                                editor.apply()
                                val i = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(i)
                                finish()
                            }else{
                                db.collection("peluqueros")
                                    .get()
                                    .addOnSuccessListener { list ->
                                        list.forEach {
                                            //binding.textView.text = binding.textView.text.toString() + "${it.id}, ${it.data["nombre"]}, ${it.data["ciudad"]}\n"
                                            if (binding.user.text.toString() == it.data["usuario"] && binding.password.text.toString() == it.data["contraseña"]) {
                                                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                                                editor.putString(USER_KEY, binding.user.text.toString())
                                                editor.putString(PWD_KEY, binding.password.text.toString())
                                                editor.apply()
                                                val i = Intent(this@LoginActivity, MainActivity::class.java)
                                                startActivity(i)
                                                finish()
                                            }else{
                                                Toast.makeText(this, "Contraseña o usuario incorrecto", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                    .addOnFailureListener {Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()}
                            }
                        }
                    }
                    .addOnFailureListener {Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()}

            }
        }
        //Si le dal an titulo te lleva de vuelta a la app
        binding.titleLogin.setOnClickListener {
            intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        //Meterse a la app si ya tenia una sesión iniciada
        if (email != "" || pwd != "") {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(USER_KEY, "")
            editor.putString(PWD_KEY, "")
            editor.apply()
            val i = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        }
    }

}