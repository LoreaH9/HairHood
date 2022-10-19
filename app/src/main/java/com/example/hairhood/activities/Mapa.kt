package com.example.hairhood.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hairhood.R

class Mapa : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.activity_mapa, com.example.hairhood.fragments.Map())
            .commit()
    }
}