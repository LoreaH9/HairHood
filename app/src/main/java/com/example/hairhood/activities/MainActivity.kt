package com.example.hairhood.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.hairhood.R
import com.example.hairhood.databinding.ActivityLoginBinding
import com.example.hairhood.databinding.ActivityMainBinding
import com.example.hairhood.fragments.*
import com.example.hairhood.fragments.Map
import com.example.hairhood.activities.LoginActivity.Companion.pelu
import com.example.hairhood.activities.LoginActivity.Companion.nombre


//SharedPreferences datos usuario
var PREFS_KEY :String = "com.example.hairhood.activities.getUser"
var USER_KEY :String = "USER_KEY"
var PWD_KEY :String = "PWD_KEY"

var user :String = ""


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var llamada: ActivityLoginBinding

    val bundle = intent?.extras
    val dato = nombre
    /*val usuario : String? = nombre*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Favorite())


        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.chat -> replaceFragment(Chat())
                R.id.profile ->
                    if (pelu == false) {
                        /*val input = dato
                        val bundle = Bundle()
                        bundle.putString("nameUser", input)*/
                        replaceFragment(Profile())
                    } else {
                        val input = dato
                        val bundle = Bundle()
                        bundle.putString("nameUser", input)
                        replaceFragment(PerfilPeluquero())
                    }
                    /*replaceFragment(Profile())*/
                R.id.favorite -> replaceFragment(Favorite())
                R.id.places -> replaceFragment(Map())
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        /*fragment.arguments = bundle*/
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
}