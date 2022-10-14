package com.example.hairhood.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.hairhood.R
import com.example.hairhood.databinding.ActivityLoginBinding
import com.example.hairhood.databinding.ActivityMainBinding
import com.example.hairhood.fragments.*
import com.example.hairhood.fragments.Map


//SharedPreferences datos usuario
var PREFS_KEY :String = "prefs"
var USER_KEY :String = "user"
var user :String = ""


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var llamada: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Favorite())
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.chat -> replaceFragment(Chat())
                R.id.profile ->
                    if (LoginActivity().pelu == true) {
                        replaceFragment(PerfilPeluquero())
                    } else {
                        replaceFragment(Profile())
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
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
}