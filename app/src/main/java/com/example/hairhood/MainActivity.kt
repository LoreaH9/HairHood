package com.example.hairhood

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.hairhood.databinding.ActivityMainBinding

var PREFS_KEY = "prefs"
var EMAIL_KEY = "email"
var email = ""

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        var sharedPreferences = getSharedPreferences(PREFS_KEY, MODE_PRIVATE)

        setContentView(binding.root)
        replaceFragment(Favorite())
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.chat -> replaceFragment(Chat())
                R.id.profile -> replaceFragment(Profile())
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