package com.example.hairhood

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.hairhood.databinding.ActivityMainBinding

var PREFS_KEY :String = "prefs"
var USER_KEY :String = "user"
var user :String = ""


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
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